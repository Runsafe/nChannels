package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.nchannels.handler.CustomChannelHandler;

public class JoinCommand extends PlayerAsyncCommand
{
	public JoinCommand(IScheduler scheduler, CustomChannelHandler handler)
	{
		super("join", "Join the channel", "runsafe.channel.<channel>.join", scheduler);
		this.handler = handler;
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		handler.join(parameters.getRequired("channel"), executor);
		return null;
	}

	private final CustomChannelHandler handler;
}
