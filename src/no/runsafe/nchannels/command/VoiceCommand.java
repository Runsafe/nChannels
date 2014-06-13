package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class VoiceCommand extends PlayerAsyncCommand
{
	public VoiceCommand(IScheduler scheduler)
	{
		super("voice", "Make someone able to talk in the channel", "runsafe.channel.<channel>.voice", scheduler, new Player().onlineOnly().require());
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return null;
	}
}
