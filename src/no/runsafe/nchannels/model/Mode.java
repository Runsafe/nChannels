package no.runsafe.nchannels.model;

public enum Mode
{
	InviteOnly(1),
	AutoJoin(2),
	Moderated(4);

	Mode(int value)
	{
		flagValue = value;
	}

	public int getFlagValue()
	{
		return flagValue;
	}

	private final int flagValue;
}
