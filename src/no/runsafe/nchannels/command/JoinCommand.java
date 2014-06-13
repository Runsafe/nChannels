package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class JoinCommand extends PlayerAsyncCommand
{
	public JoinCommand(IScheduler scheduler)
	{
		super("join", "Join the channel", "runsafe.channel.<channel>.join", scheduler);
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
