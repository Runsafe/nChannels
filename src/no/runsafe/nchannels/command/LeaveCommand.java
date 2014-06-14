package no.runsafe.nchannels.command;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.nchannels.handler.CustomChannelHandler;

public class LeaveCommand extends PlayerAsyncCommand
{
	public LeaveCommand(IScheduler scheduler, CustomChannelHandler handler)
	{
		super("leave", "Leave the channel", "runsafe.channel.<channel>.leave", scheduler, new TrailingArgument("reason", false));
		this.handler = handler;
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		handler.leave((String) parameters.getRequired("channel"), executor, (String) parameters.getValue("reason"));
		return null;
	}

	private final CustomChannelHandler handler;
}
