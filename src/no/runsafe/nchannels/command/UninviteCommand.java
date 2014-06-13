package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class UninviteCommand extends PlayerAsyncCommand
{
	public UninviteCommand(IScheduler scheduler)
	{
		super("uninvite", "Retract someones invitation to the channel", "runsafe.channel.<channel>.uninvite", scheduler, new Player().require());
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
