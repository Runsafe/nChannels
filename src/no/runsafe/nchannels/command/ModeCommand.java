package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.BooleanArgument;
import no.runsafe.framework.api.command.argument.Enumeration;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.nchannels.handler.CustomChannelHandler;
import no.runsafe.nchannels.model.Mode;

public class ModeCommand extends PlayerAsyncCommand
{
	public ModeCommand(IScheduler scheduler, CustomChannelHandler handler)
	{
		super("mode", "Change channel mode flags", "runsafe.channel.<channel>.mode", scheduler, new Enumeration("mode", Mode.values()).require(), new BooleanArgument("on").require());
		this.handler = handler;
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		handler.setFlag(parameters.getRequired("channel"), parameters.getRequired("mode"),
		                parameters.getRequired("on")
		);
		return null;
	}

	private final CustomChannelHandler handler;
}
