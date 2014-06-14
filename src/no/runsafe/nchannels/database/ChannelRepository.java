package no.runsafe.nchannels.database;

import no.runsafe.framework.api.database.*;
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
		update.addQueries(
			"CREATE TABLE nchannel_channels (" +
				"name VARCHAR(10) NOT NULL," +
				"int mode NOT NULL," +
				"PRIMARY KEY (name)" +
				")"
		);
		return update;
	}

	public boolean addChannel(String name)
	{
		String check = database.queryString("SELECT name FROM nchannel_channels WHERE name=?", name);
		if (check != null)
			return false;
		return database.execute("INSERT INTO nchannel_channels (name, mode) VALUES (?, ?)", name, 0);
	}

	public boolean removeChannel(String name)
	{
		return database.execute("DELETE FROM nchannel_channels WHERE name=?", name);
	}

	public void setChannelMode(String name, Mode mode, boolean enabled)
	{
		int flags = database.queryInteger("SELECT mode FROM nchannel_channels WHERE name=?", name);
		flags = enabled ? flags | mode.getFlagValue() : flags & ~mode.getFlagValue();
		database.update("UPDATE nchannel_channels SET mode=? WHERE name=?", name, flags);
	}

	public List<CustomChatChannel> getChannels()
	{
		List<CustomChatChannel> channels = new ArrayList<CustomChatChannel>(0);
		ISet channelSet = database.query("SELECT * FROM nchannel_channels");
		for (IRow channel : channelSet)
			channels.add(new CustomChatChannel(console, manager, channel.String("name"), channel.Integer("mode")));
		return channels;
	}

	private IConsole console;
	private IChannelManager manager;
}
