package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class InviteCommand extends PlayerAsyncCommand
{
	protected InviteCommand(IScheduler scheduler)
	{
		super("invite", "Invite someone to join your channel", "runsafe.channel.<channel>.invite", scheduler, new Player().onlineOnly().require());
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
