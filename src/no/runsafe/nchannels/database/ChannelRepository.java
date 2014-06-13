package no.runsafe.nchannels.database;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.nchannels.model.CustomChatChannel;
import no.runsafe.nchannels.model.Mode;
import no.runsafe.nchat.channel.IChannelManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ChannelRepository extends Repository
{
	public ChannelRepository(IConsole console, IChannelManager manager)
	{
		this.console = console;
		this.manager = manager;
	}

	@Nonnull
	@Override
	public String getTableName()
	{
		return "nchannel_channels";
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();
		return update;
	}

	public void addChannel(String name)
	{
	}

	public void setChannelMode(String name, Mode mode, boolean enabled)
	{
	}

	public List<CustomChatChannel> getChannels()
	{
		List<CustomChatChannel> channels = new ArrayList<CustomChatChannel>(0);
//		for (...)
//			channels.add(new CustomChatChannel(console, manager, ..));
		return channels;
	}

	private IConsole console;
	private IChannelManager manager;
}
