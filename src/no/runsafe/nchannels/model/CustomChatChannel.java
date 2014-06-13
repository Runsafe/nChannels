package no.runsafe.nchannels.model;

import no.runsafe.framework.api.log.IConsole;
import no.runsafe.nchat.channel.BasicChatChannel;
import no.runsafe.nchat.channel.IChannelManager;

public class CustomChatChannel extends BasicChatChannel
{
	public CustomChatChannel(IConsole console, IChannelManager manager, String name)
	{
		super(console, manager, name);
	}
}
