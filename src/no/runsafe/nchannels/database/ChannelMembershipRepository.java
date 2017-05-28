package no.runsafe.nchannels.database;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	public List<String> getPlayerChannels(IPlayer player)
	{
		return database.queryStrings("SELECT channel FROM nchannel_members WHERE player=?", player.getUniqueId().toString());
	}

	public List<UUID> getChannelPlayers(String channel)
	{
		List<String> stringIds = database.queryStrings("SELECT player FROM nchannel_members WHERE channel=?", channel);
		List<UUID> playerIds = new ArrayList<UUID>();
		for(String playerStringId : stringIds)
			playerIds.add(UUID.fromString(playerStringId));

		return playerIds;
	}

	public boolean addPlayerToChannel(String channel, IPlayer player)
	{
		return database.execute("INSERT INTO nchannel_members (channel, player) VALUES (?, ?)", channel, player.getUniqueId().toString());
	}

	public boolean removePlayerFromChannel(String channel, IPlayer player)
	{
		return database.execute("DELETE FROM nchannel_members WHERE channel=? AND player=?", channel, player.getUniqueId().toString());
	}

	public boolean clearChannel(String channel)
	{
		return database.execute("DELETE FROM nchannel_members WHERE channel=?", channel);
	}

	public boolean clearPlayer(IPlayer player)
	{
		return database.execute("DELETE FROM nchannel_members WHERE player=?", player.getUniqueId().toString());
	}
}
