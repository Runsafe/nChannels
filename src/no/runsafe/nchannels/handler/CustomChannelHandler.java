package no.runsafe.nchannels.handler;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.event.player.IPlayerJoinEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.nchannels.database.ChannelMembershipRepository;
import no.runsafe.nchannels.database.ChannelRepository;
import no.runsafe.nchannels.model.CustomChatChannel;
import no.runsafe.nchannels.model.Mode;
import no.runsafe.nchat.channel.IChannelManager;
import no.runsafe.nchat.channel.IChatChannel;

import java.util.ArrayList;
import java.util.List;

public class CustomChannelHandler implements IServerReady, IPlayerJoinEvent
{
	public CustomChannelHandler(IConsole console, IServer server, IChannelManager manager, ChannelRepository channelRepository, ChannelMembershipRepository channelMembershipRepository)
	{
		this.console = console;
		this.server = server;
		this.manager = manager;
		this.channelRepository = channelRepository;
		this.channelMembershipRepository = channelMembershipRepository;
	}

	@Override
	public void OnServerReady()
	{
		for (CustomChatChannel channel : channelRepository.getChannels())
		{
			manager.registerChannel(channel);
			if (channel.isFlagSet(Mode.AutoJoin))
				autoJoinChannels.add(channel);
		}
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event)
	{
		if (event.isFake())
			return;
		IPlayer player = event.getPlayer();
		for (IChatChannel channel : autoJoinChannels)
			ProcessAutoJoin(player, channel);
	}

	public void join(String channelName, IPlayer player)
	{
		IChatChannel channel = manager.getChannelByName(channelName);

		if (channel == null)
			channel = createChannel(channelName);

		// Protect channels we don't manage and autojoin from being joined
		if (!(channel instanceof CustomChatChannel) || ((CustomChatChannel) channel).isFlagSet(Mode.AutoJoin))
			return;

		if (channel.Join(player))
		{
			channelMembershipRepository.addPlayerToChannel(channelName, player);
			channel.SendSystem(player.getPrettyName() + " has joined the channel!");
		}
	}

	public void leave(String channelName, IPlayer player, String reason)
	{
		IChatChannel channel = manager.getChannelByName(channelName);

		// Protect channels we don't manage and autojoin from being left
		if (!(channel instanceof CustomChatChannel) || ((CustomChatChannel) channel).isFlagSet(Mode.AutoJoin))
			return;

		if (channel.Leave(player))
		{
			channelMembershipRepository.removePlayerFromChannel(channelName, player);
			channel.SendSystem(player.getPrettyName() + " has left the channel.");
			if (channelMembershipRepository.getChannelPlayers(channelName).isEmpty())
				if(channelRepository.removeChannel(channelName))
					manager.unregisterChannel(channel);
		}
	}

	public void setFlag(String channelName, Mode mode, boolean on)
	{
		IChatChannel channel = manager.getChannelByName(channelName);

		// Protect channels we don't manage from being joined
		if (!(channel instanceof CustomChatChannel))
			return;

		((CustomChatChannel) channel).setFlag(mode, on);
		channelRepository.setChannelMode(channelName, mode, on);
		if (mode == Mode.AutoJoin)
		{
			if (on)
				CreateAutoJoinChannel((CustomChatChannel) channel);
			else
				DestroyAutoJoinChannel((CustomChatChannel) channel);
		}
	}

	private IChatChannel createChannel(String channelName)
	{
		channelRepository.addChannel(channelName);
		CustomChatChannel channel = new CustomChatChannel(console, manager, channelName, 0);
		manager.registerChannel(channel);
		return channel;
	}

	private void DestroyAutoJoinChannel(CustomChatChannel channel)
	{
		autoJoinChannels.remove(channel);
		channel.Clear();
	}

	private void CreateAutoJoinChannel(CustomChatChannel channel)
	{
		autoJoinChannels.add(channel);
		channelMembershipRepository.clearChannel(channel.getName());
		for (IPlayer player : server.getOnlinePlayers())
			ProcessAutoJoin(player, channel);
	}

	private void ProcessAutoJoin(IPlayer player, IChatChannel channel)
	{
		if (player.hasPermission("runsafe.channel." + channel.getName() + ".autojoin"))
			channel.Join(player);
	}

	private final IConsole console;
	private final IServer server;
	private final IChannelManager manager;
	private final ChannelRepository channelRepository;
	private final ChannelMembershipRepository channelMembershipRepository;
	private final List<CustomChatChannel> autoJoinChannels = new ArrayList<CustomChatChannel>(0);
}
