package uk.co.redfruit.libraries.srpDB.utils;

import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

public class ContentCleaner {
	
	private static final HtmlCleaner CLEANER = new HtmlCleaner();
	
	public static String cleanContent(String content) {
		return cleanContent(content, 0);
	}

	public static String cleanContent(String content, int displayWidth) {
		CleanerProperties props = CLEANER.getProperties();
		props.setOmitComments(true);
		TagNode node = CLEANER.clean(content);
		cleanImgTags(node, displayWidth);
		removeATagsPreservingContent(node);
		removeCopyForForumTable(node);
		//removeImageText(node);
		String cleaned = new PrettyXmlSerializer(props).getAsString(node);
		
		return cleaned;
	}

	private static void removeImageText(TagNode node) {
		TagNode[] thTagsWithImageText = node.getElementsByName("th", true);
		for (TagNode currentNode : thTagsWithImageText) {
			String currentContent = currentNode.getText().toString().trim();
			if ("Image".equals(currentContent)) {
				TagNode trNode = currentNode.getParent();
				TagNode[] imageTags = trNode.getElementsByName("img", true);
				TagNode tableTag = trNode.getParent();
				for (TagNode currentImgTag : imageTags) {
					
					node.insertChildAfter(tableTag, currentImgTag);
				}
				trNode.removeFromTree();
			}
		}
	}

	private static void removeCopyForForumTable(TagNode node) {
		TagNode[] styledTags = node.getElementsHavingAttribute("style", true);
		for (TagNode currentNode : styledTags) {
			if (currentNode.getName().equalsIgnoreCase("table")) {
				String style = currentNode.getAttributeByName("style");
				if("solid;float:right".equalsIgnoreCase(style)) {
					currentNode.removeFromTree();
				}
			}
		}
	}

	private static void removeATagsPreservingContent(TagNode node) {
		TagNode[] aTags = node.getElementsByName("a", true);
		for (TagNode currentNode : aTags) {
			TagNode parent = currentNode.getParent();
			List<TagNode> linkText = currentNode.getAllChildren();
			currentNode.removeFromTree();
			parent.addChildren(linkText);
		}
	}

	private static void cleanImgTags(TagNode node, int width) {
		TagNode[] imgTags = node.getElementsByName("img", true);
		for (TagNode currentNode : imgTags) {
			currentNode.removeAttribute("width");
			currentNode.removeAttribute("height");
			if (width != 0) {
				currentNode.addAttribute("width", Integer.toString(width));
			}
		}
	}

}
