package no.runsafe.nchannels.event;

import no.runsafe.framework.api.event.player.IPlayerJoinEvent;
import no.runsafe.framework.api.event.player.IPlayerKickEvent;
import no.runsafe.framework.api.event.player.IPlayerQuitEvent;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerKickEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerQuitEvent;
import no.runsafe.nchannels.database.ChannelMembershipRepository;
import no.runsafe.nchannels.model.CustomChatChannel;
import no.runsafe.nchat.channel.IChannelManager;
import no.runsafe.nchat.channel.IChatChannel;

public class PlayerEvents implements IPlayerQuitEvent, IPlayerJoinEvent, IPlayerKickEvent
{
	public PlayerEvents(IDebug debugger, IChannelManager manager, ChannelMembershipRepository channelMembershipRepository)
	{
		this.debugger = debugger;
		this.manager = manager;
		this.channelMembershipRepository = channelMembershipRepository;
	}

	@Override
	public void OnPlayerQuit(RunsafePlayerQuitEvent event)
	{
		if (event.isFake())
			return;
		for (IChatChannel channel : manager.getChannels(event.getPlayer().getName()))
			if (channel instanceof CustomChatChannel)
				channel.Leave(event.getPlayer());
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event)
	{
		if (event.isFake())
			return;
		IPlayer player = event.getPlayer();
		debugger.debugFine("Processing join server event for " + player.getName());
		for (String channel : channelMembershipRepository.getPlayerChannels(player.getName()))
			manager.getChannelByName(channel).Join(player);
	}

	@Override
	public void OnPlayerKick(RunsafePlayerKickEvent event)
	{
		if (event.getPlayer().isNotBanned())
			return;
		channelMembershipRepository.clearPlayer(event.getPlayer().getName());
	}

	private final IDebug debugger;
	private final IChannelManager manager;
	private final ChannelMembershipRepository channelMembershipRepository;
}
