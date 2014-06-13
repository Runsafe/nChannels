package no.runsafe.nchannels;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;
import no.runsafe.nchannels.command.*;

public class nChannels extends RunsafeConfigurablePlugin
{
//	public static IDebug Debugger = null;

	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Commands.class);
		addComponent(Database.class);
		addComponent(Events.class);

		// Plugin components
		Command command = new Command("channel", "Manage chat channels", null, new RequiredArgument("channel"));
		command.addSubCommand(getInstance(ModeCommand.class));
		command.addSubCommand(getInstance(InviteCommand.class));
		command.addSubCommand(getInstance(UninviteCommand.class));
		command.addSubCommand(getInstance(JoinCommand.class));
		command.addSubCommand(getInstance(PartCommand.class));
		command.addSubCommand(getInstance(VoiceCommand.class));
		command.addSubCommand(getInstance(KickCommand.class));
	}
}
