package com.kqds.util.wx.face;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QQFaceUtil {

	public static Map<String, String> faceMap = new HashMap<String, String>();
	public static List<String> faceList = new ArrayList<String>();
	public static List<String> faceDescList = new ArrayList<String>();

	static {
		faceDescList.add("微笑");
		faceDescList.add("撇嘴");
		faceDescList.add("色");
		faceDescList.add("发呆");
		faceDescList.add("得意");
		faceDescList.add("流泪");
		faceDescList.add("害羞");
		faceDescList.add("闭嘴");
		faceDescList.add("睡");
		faceDescList.add("大哭");
		faceDescList.add("尴尬");
		faceDescList.add("发怒");
		faceDescList.add("调皮");
		faceDescList.add("呲牙");
		faceDescList.add("惊讶");

		faceDescList.add("难过");
		faceDescList.add("酷");
		faceDescList.add("冷汗");
		faceDescList.add("抓狂");
		faceDescList.add("吐");
		faceDescList.add("偷笑");
		faceDescList.add("愉快");
		faceDescList.add("白眼");
		faceDescList.add("傲慢");
		faceDescList.add("饥饿");
		faceDescList.add("困");
		faceDescList.add("惊恐");
		faceDescList.add("流汗");
		faceDescList.add("憨笑");
		faceDescList.add("悠闲");

		faceDescList.add("奋斗");
		faceDescList.add("咒骂");
		faceDescList.add("疑问");
		faceDescList.add("嘘");
		faceDescList.add("晕");
		faceDescList.add("疯了");
		faceDescList.add("衰");
		faceDescList.add("骷髅");
		faceDescList.add("敲打");
		faceDescList.add("再见");
		faceDescList.add("擦汗");
		faceDescList.add("抠鼻");
		faceDescList.add("鼓掌");
		faceDescList.add("糗大了");
		faceDescList.add("坏笑");

		faceDescList.add("左哼哼");
		faceDescList.add("右哼哼");
		faceDescList.add("哈欠");
		faceDescList.add("鄙视");
		faceDescList.add("委屈");
		faceDescList.add("快哭了");
		faceDescList.add("阴险");
		faceDescList.add("亲亲");
		faceDescList.add("吓");
		faceDescList.add("可怜");
		faceDescList.add("菜刀");
		faceDescList.add("西瓜");
		faceDescList.add("啤酒");
		faceDescList.add("篮球");
		faceDescList.add("乒乓");

		faceDescList.add("咖啡");
		faceDescList.add("饭");
		faceDescList.add("猪头");
		faceDescList.add("玫瑰");
		faceDescList.add("凋谢");
		faceDescList.add("嘴唇");
		faceDescList.add("爱心");
		faceDescList.add("心碎");
		faceDescList.add("蛋糕");
		faceDescList.add("闪电");
		faceDescList.add("炸弹");
		faceDescList.add("刀");
		faceDescList.add("足球");
		faceDescList.add("瓢虫");
		faceDescList.add("便便");

		faceDescList.add("月亮");
		faceDescList.add("太阳");
		faceDescList.add("礼物");
		faceDescList.add("拥抱");
		faceDescList.add("强");
		faceDescList.add("弱");
		faceDescList.add("握手");
		faceDescList.add("胜利");
		faceDescList.add("抱拳");
		faceDescList.add("勾引");
		faceDescList.add("拳头");
		faceDescList.add("差劲");
		faceDescList.add("爱你");
		faceDescList.add("NO");
		faceDescList.add("OK");

		faceDescList.add("爱情");
		faceDescList.add("飞吻");
		faceDescList.add("跳跳");
		faceDescList.add("发抖");
		faceDescList.add("怄火");
		faceDescList.add("转圈");
		faceDescList.add("磕头");
		faceDescList.add("回头");
		faceDescList.add("跳绳");
		faceDescList.add("投降");
		faceDescList.add("激动");
		faceDescList.add("乱舞");
		faceDescList.add("献吻");
		faceDescList.add("左太极");
		faceDescList.add("右太极");

		// [微笑][撇嘴][色][发呆][得意][流泪][害羞][闭嘴][睡][大哭][尴尬][发怒][调皮][呲牙][惊讶]
		faceList.add("/::)");
		faceList.add("/::~");
		faceList.add("/::B");
		faceList.add("/::|");
		faceList.add("/:8-)");
		faceList.add("/::<");
		faceList.add("/::$");
		faceList.add("/::X");
		faceList.add("/::Z");
		faceList.add("/::'(");
		faceList.add("/::-|");
		faceList.add("/::@");
		faceList.add("/::P");
		faceList.add("/::D");
		faceList.add("/::O");
		// [难过][酷][冷汗][抓狂][吐][偷笑][愉快][白眼][傲慢][饥饿][困][惊恐][流汗][憨笑][悠闲]
		faceList.add("/::(");
		faceList.add("/::+");
		faceList.add("/:--b");
		faceList.add("/::Q");
		faceList.add("/::T");
		faceList.add("/:,@P");
		faceList.add("/:,@-D");
		faceList.add("/::d");
		faceList.add("/:,@o");
		faceList.add("/::g");
		faceList.add("/:|-)");
		faceList.add("/::!");
		faceList.add("/::L");
		faceList.add("/::>");
		faceList.add("/::,@");
		// [奋斗][咒骂][疑问][嘘][晕][疯了][衰][骷髅][敲打][再见][擦汗][抠鼻][鼓掌][糗大了][坏笑]
		faceList.add("/:,@f");
		faceList.add("/::-S");
		faceList.add("/:?");
		faceList.add("/:,@x");
		faceList.add("/:,@@");
		faceList.add("/::8");
		faceList.add("/:,@!");
		faceList.add("/:!!!");
		faceList.add("/:xx");
		faceList.add("/:bye");
		faceList.add("/:wipe");
		faceList.add("/:dig");
		faceList.add("/:handclap");
		faceList.add("/:&-(");
		faceList.add("/:B-)");
		// [左哼哼][右哼哼][哈欠][鄙视][委屈][快哭了][阴险][亲亲][吓][可怜][菜刀][西瓜][啤酒][篮球][乒乓]
		faceList.add("/:<@");
		faceList.add("/:@>");
		faceList.add("/::-O");
		faceList.add("/:>-|");
		faceList.add("/:P-(");
		faceList.add("/::'|");
		faceList.add("/:X-)");
		faceList.add("/::*");
		faceList.add("/:@x");
		faceList.add("/:8*");
		faceList.add("/:pd");
		faceList.add("/:<W>");
		faceList.add("/:beer");
		faceList.add("/:basketb");
		faceList.add("/:oo");
		// [咖啡][饭][猪头][玫瑰][凋谢][嘴唇][爱心][心碎][蛋糕][闪电][炸弹][刀][足球][瓢虫][便便]
		faceList.add("/:coffee");
		faceList.add("/:eat");
		faceList.add("/:pig");
		faceList.add("/:rose");
		faceList.add("/:fade");
		faceList.add("/:showlove");
		faceList.add("/:heart");
		faceList.add("/:break");
		faceList.add("/:cake");
		faceList.add("/:li");
		faceList.add("/:bome");
		faceList.add("/:kn");
		faceList.add("/:footb");
		faceList.add("/:ladybug");
		faceList.add("/:shit");
		// [月亮][太阳][礼物][拥抱][强][弱][握手][胜利][抱拳][勾引][拳头][差劲][爱你][NO][OK]
		faceList.add("/:moon");
		faceList.add("/:sun");
		faceList.add("/:gift");
		faceList.add("/:hug");
		faceList.add("/:strong");
		faceList.add("/:weak");
		faceList.add("/:share");
		faceList.add("/:v");
		faceList.add("/:@)");
		faceList.add("/:jj");
		faceList.add("/:@@");
		faceList.add("/:bad");
		faceList.add("/:lvu");
		faceList.add("/:no");
		faceList.add("/:ok");
		// [爱情][飞吻][跳跳][发抖][怄火][转圈][磕头][回头][跳绳][投降][激动][乱舞][献吻][左太极][右太极]
		faceList.add("/:love");
		faceList.add("/:<L>");
		faceList.add("/:jump");
		faceList.add("/:shake");
		faceList.add("/:<O>");
		faceList.add("/:circle");
		faceList.add("/:kotow");
		faceList.add("/:turn");
		faceList.add("/:skip");
		faceList.add("/:oY");
		faceList.add("/:#-0");
		faceList.add("/:hiphot");
		faceList.add("/:kiss");
		faceList.add("/:<&");
		faceList.add("/:&>");

		for (int i = 0; i < faceList.size(); i++) {
			faceMap.put(faceList.get(i), "qqface" + i);
		}
	}

	public static String dealQQFace(String content) {
		for (int i = 0; i < faceList.size(); i++) {
			content = content.replace(faceList.get(i), getFaceCssImg(i));
			content = content.replace("[" + faceDescList.get(i) + "]", getFaceCssImg(i));
		}
		return content;
	}

	public static String getFaceCssImg(int i) {
		String faceName = faceList.get(i);
		String faceTitle = faceDescList.get(i);
		String css = faceMap.get(faceName);
		return "&lt;img class='qqface " + css + "' title='" + faceTitle + "' src='static/image/kqdsFront/wechat/chat/spacer.gif'&gt;";
		/** 注意转义字符 **/
	}
}
