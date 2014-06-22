package no.runsafe.nchannels;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;
import no.runsafe.nchannels.command.JoinCommand;
import no.runsafe.nchannels.command.LeaveCommand;
import no.runsafe.nchannels.command.ModeCommand;
import no.runsafe.nchannels.database.ChannelMembershipRepository;
import no.runsafe.nchannels.database.ChannelRepository;
import no.runsafe.nchannels.event.PlayerEvents;
import no.runsafe.nchannels.handler.CustomChannelHandler;

public class nChannels extends RunsafePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Commands.class);
		addComponent(Database.class);
		addComponent(Events.class);

		// Plugin components
		addComponent(PlayerEvents.class);
		addComponent(ChannelRepository.class);
		addComponent(ChannelMembershipRepository.class);
		addComponent(CustomChannelHandler.class);

		// Commands
		Command command = new Command("channel", "Manage chat channels", null, new RequiredArgument("channel"));
		command.addSubCommand(getInstance(JoinCommand.class));
		command.addSubCommand(getInstance(LeaveCommand.class));
		command.addSubCommand(getInstance(ModeCommand.class));
		// Not yet implemented
//		command.addSubCommand(getInstance(InviteCommand.class));
//		command.addSubCommand(getInstance(UninviteCommand.class));
//		command.addSubCommand(getInstance(VoiceCommand.class));
//		command.addSubCommand(getInstance(KickCommand.class));
		addComponent(command);
	}
}
