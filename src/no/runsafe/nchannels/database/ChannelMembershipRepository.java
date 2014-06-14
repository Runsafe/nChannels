package no.runsafe.nchannels.database;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;

import javax.annotation.Nonnull;
import java.util.List;

public class ChannelMembershipRepository extends Repository
{
	@Nonnull
	@Override
	public String getTableName()
	{
		return "mchannel_members";
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();
		update.addQueries(
			"CREATE TABLE nchannel_members (" +
				"channel VARCHAR(10) NOT NULL," +
				"player VARCHAR(20) NOT NULL," +
				"PRIMARY KEY (channel, player)" +
				")"
		);
		return update;
	}

	public List<String> getPlayerChannels(String player)
	{
		return database.queryStrings("SELECT channel FROM nchannel_members WHERE player=?", player);
	}

	public List<String> getChannelPlayers(String channel)
	{
		return database.queryStrings("SELECT player FROM nchannel_members WHERE channel=?", channel);
	}

	public boolean addPlayerToChannel(String channel, String player)
	{
		return database.execute("INSERT INTO nchannel_members (channel, player) VALUES (?, ?)", channel, player);
	}

	public boolean removePlayerFromChannel(String channel, String player)
	{
		return database.execute("DELETE FROM nchannel_members WHERE channel=? AND player=?", channel, player);
	}

	public boolean clearChannel(String channel)
	{
		return database.execute("DELETE FROM nchannel_members WHERE channel=?", channel);
	}

	public boolean clearPlayer(String player)
	{
		return database.execute("DELETE FROM nchannel_members WHERE player=?", player);
	}
}
