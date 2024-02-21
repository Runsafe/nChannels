package no.runsafe.nchannels.database;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nonnull;
import java.util.List;

public class ChannelMembershipRepository extends Repository
{
	public ChannelMembershipRepository()
	{
	}

	@Nonnull
	@Override
	public String getTableName()
	{
		return "nchannel_members";
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
		update.addQueries("ALTER TABLE nchannel_members MODIFY COLUMN player VARCHAR(36)");

		update.addQueries( // Update UUIDs
			String.format(
				"UPDATE IGNORE `%s` SET `player` = " +
					"COALESCE((SELECT `uuid` FROM player_db WHERE `name`=`%s`.`player`), `player`) " +
					"WHERE length(`player`) != 36",
				getTableName(), getTableName()
			)
		);
		return update;
	}

	public Boolean isEmptyChannel(String channel)
	{
		List<String> stringIds = database.queryStrings("SELECT player FROM nchannel_members WHERE channel=?", channel);
		return stringIds.isEmpty();
	}

	public List<String> getPlayerChannels(IPlayer player)
	{
		return database.queryStrings("SELECT channel FROM nchannel_members WHERE player=?", player.getUniqueId().toString());
	}

	public List<IPlayer> getChannelPlayers(String channel)
	{
		return database.queryPlayers("SELECT player FROM nchannel_members WHERE channel=?", channel);
	}

	public void addPlayerToChannel(String channel, IPlayer player)
	{
		database.execute("INSERT INTO nchannel_members (channel, player) VALUES (?, ?)", channel, player.getUniqueId().toString());
	}

	public void removePlayerFromChannel(String channel, IPlayer player)
	{
		database.execute("DELETE FROM nchannel_members WHERE channel=? AND player=?", channel, player.getUniqueId().toString());
	}

	public void clearChannel(String channel)
	{
		database.execute("DELETE FROM nchannel_members WHERE channel=?", channel);
	}

	public void clearPlayer(IPlayer player)
	{
		database.execute("DELETE FROM nchannel_members WHERE player=?", player.getUniqueId().toString());
	}
}
