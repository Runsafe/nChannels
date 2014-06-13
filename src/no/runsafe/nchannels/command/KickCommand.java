package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class KickCommand extends PlayerAsyncCommand
{
	public KickCommand(IScheduler scheduler)
	{
		super("kick", "Kick someone from the channel", "runsafe.channel.<channel>.kick", scheduler, new Player().onlineOnly().require());
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
