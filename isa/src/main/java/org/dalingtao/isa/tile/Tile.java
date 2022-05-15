package org.dalingtao.isa.tile;

import org.dalingtao.isa.abstractasm.Ins;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tile {
    String format;
    TileNode root;
    long cost;
    List<InsFactory> instructs = new ArrayList<>();

    @Override
    public String toString() {
        return format;
    }
}
