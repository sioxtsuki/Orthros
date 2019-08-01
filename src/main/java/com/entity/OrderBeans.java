package com.entity;

public class OrderBeans
{
	String server;
	String symbol;
	String cmd;
	double lot;

	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getLot() {
		return lot;
	}
	public void setLot(double lot) {
		this.lot = lot;
	}

}
