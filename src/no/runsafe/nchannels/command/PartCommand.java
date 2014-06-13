package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class PartCommand extends PlayerAsyncCommand
{
	public PartCommand(IScheduler scheduler)
	{
		super("part", "Leave the channel", "runsafe.channel.<channel>.part", scheduler, new TrailingArgument("reason", false));
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
