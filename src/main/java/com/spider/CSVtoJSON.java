package com.spider;

import au.com.bytecode.opencsv.CSVReader;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CSVtoJSON {
	String inFile;

	public CSVtoJSON(String inFile) {
		this.inFile = inFile;
	}

	public static void main(String[] args) throws Exception {
		CSVtoJSON c2j = new CSVtoJSON(args[0]);
		String json = c2j.run();

		FileWriter fw = new FileWriter(new File(args[1]));
		fw.write(json);
		fw.close();
	}

	public String run() throws Exception {
		CSVReader reader = new CSVReader(new FileReader(inFile),',');
		List<String[]> lines = reader.readAll();
		reader.close();

		Map<Integer,TreeNode> lastItemAtDepths = new HashMap();

		TreeNode root = new TreeNode();
		root.setKey("Root");
		root.setLabel("Root");
		lastItemAtDepths.put(0, root);

		Set<String> createdKeys = new HashSet<>();

		//Skip the header
		for(String[] lineValues: lines.subList(1,lines.size())) {

			int depthCounter = 0;
			for(String val: lineValues) {
				//for each value in the row
				depthCounter++;

				if (!val.isEmpty() && !createdKeys.contains(val)) {
					//Create a new TreeNode
					TreeNode tn = new TreeNode();
					tn.setKey(getKey(val));
					tn.setLabel(val);
					createdKeys.add(val);

					//Find parent, might not necessarily be only one level above
					for(int i = depthCounter - 1; i >= 0; i--) {
						TreeNode parentNode = lastItemAtDepths.get(i);
						if(parentNode != null) {
							//If the parentNode is null, then this is the root. If the parentNode exists, add this as a child
							parentNode.getChildren().add(tn);
							break;
						}
					}

					//Note the most recent item at this depth, because this one might have children too
					lastItemAtDepths.put(depthCounter, tn);
				}
			}
		}

		//At the end, spit this all out
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		return objectMapper.writeValueAsString(lastItemAtDepths.get(0));
	}

	//Keys can be included within parenthesis
	public static String getKey(String label){
		int startOfId = label.lastIndexOf("(") + 1;
		if(startOfId > 0) {
			label = label.substring(startOfId, label.length() - 1);
			if(label.contains(",")) {
				return label.split(",")[0];
			}
		}
		return label.trim();
	}
}
