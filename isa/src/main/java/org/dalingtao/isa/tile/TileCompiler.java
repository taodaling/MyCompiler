package org.dalingtao.isa.tile;

import org.dalingtao.MultiProperties;
import org.dalingtao.StringReader;
import org.dalingtao.StringUtil;
import org.dalingtao.isa.abstractasm.Ins;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TileCompiler {
    MultiProperties properties;

    public TileChooser compile(ISACost costs, InputStream is) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        properties = new MultiProperties(is);
        List<Tile> tiles = new ArrayList<>();
        for (var entry : properties.items.entrySet()) {
            Tile tile = new Tile();
            String line = entry.getKey();
            tile.format = entry.getKey();
            reader = new StringReader(line, true);
            tile.root = parse();
            if (reader.peek() != -1) {
                throw new TileCompilerException("Not support multiple IR codes: " + reader);
            }
            for (String cmds : entry.getValue()) {
                String substr = cmds.substring(1).trim();
                var insFactory = parseIns(substr);
                tile.instructs.add(insFactory);
                tile.cost += costs.cost(insFactory.args.get(0));
            }
            tiles.add(tile);
        }
        return new TileChooser(tiles);
    }

    private InsFactory parseIns(String cmd) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int index = cmd.indexOf(':');
        List<String> left = StringUtil.split(cmd.substring(0, index));
        String right = cmd.substring(index + 1).trim();
        Class<? extends Ins> cls = (Class<? extends Ins>) Class.forName(properties.property.get("classPrefix") + right);
        return new InsFactory(cls.getConstructor(String[].class), left);
    }

    StringReader reader;

    boolean isWord(int x) {
        return x >= '0' && x <= '9' || x >= 'a' && x <= 'z' ||
                x >= 'A' && x <= 'Z' || x == '_' || x == '$';
    }

    String readName() {
        StringBuilder builder = new StringBuilder();
        while (isWord(reader.peek())) {
            builder.append((char) reader.read());
        }
        return builder.toString();
    }


    TileNode parse() throws ClassNotFoundException {
        String left = readName();
        String right = null;
        if (reader.peek() == ':') {
            reader.read();
            right = readName();
        }
        TileNode root = new TileNode();
        boolean isLeaf = true;
        if (reader.peek() == '(') {
            isLeaf = false;
            //recursive
            reader.read();
            while (reader.peek() != ')') {
                root.adj.add(parse());
                if (reader.peek() == ',') {
                    reader.read();
                } else if (reader.peek() == ')') {
                    break;
                } else {
                    throw new TileCompilerException("Unexpected token: " + reader.toString());
                }
            }
            if (reader.read() != ')') {
                throw new TileCompilerException("Should close with ): " + reader);
            }
        }
        if (isLeaf && right != null) {
            throw new TileCompilerException("Not support alias:Type: " + reader.toString());
        }
        String clsName = null;
        if (right != null) {
            root.alias = left;
            clsName = right;
        } else if (isLeaf) {
            root.alias = left;
        } else {
            clsName = left;
        }
        root.type = clsName == null ? Object.class :
                Class.forName("org.dalingtao.sa.ir." + clsName);
        return root;
    }
}
