package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class LeaveCommand extends PlayerAsyncCommand
{
	public LeaveCommand(IScheduler scheduler)
	{
		super("leave", "Leave the channel", "runsafe.channel.<channel>.leave", scheduler, new TrailingArgument("reason", false));
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
