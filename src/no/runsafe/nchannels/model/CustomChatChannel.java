package no.runsafe.nchannels.model;

import no.runsafe.framework.api.log.IConsole;
import no.runsafe.nchat.channel.BasicChatChannel;
import no.runsafe.nchat.channel.IChannelManager;

import java.util.ArrayList;
import java.util.List;

public class CustomChatChannel extends BasicChatChannel
{
	public CustomChatChannel(IConsole console, IChannelManager manager, String name, Integer mode)
	{
		super(console, manager, name);
		for (Mode flag : Mode.values())
			if ((mode & flag.getFlagValue()) > 0)
				flags.add(flag);
	}

	public void setFlag(Mode flag, boolean on)
	{
		if (on && !flags.contains(flag))
			flags.add(flag);
		if (!on && flags.contains(flag))
			flags.remove(flag);
	}

	public boolean isFlagSet(Mode flag)
	{
		return flags.contains(flag);
	}

	private final List<Mode> flags = new ArrayList<Mode>(0);
}
