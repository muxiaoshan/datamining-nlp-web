package com.sam.nlp.web;

import java.util.List;

import org.junit.Test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

/**
 * 获取文本中某一词典类型中的关键词
 * @author lenovo-pc
 *
 */
public class GetAllByDictType {

	/**
	 * 获取文本中某一类型的所有关键词
	 */
	@Test
	public void getKeywordsByDictType() {
		String input = "习近平欢迎潘基文秘书长第十次访华，并积极评价潘基文担任联合国秘书长10年来为维护世界和平、促进国际社会可持续发展、应对气候变化等全球性挑战所作努力，为加强联合国同中国合作所作贡献。习近平指出，当前中国同联合国合作不断深化。作为联合国安理会常任理事国和最大的发展中国家，中国将继续做联合国的坚定支持者、维护者、参与者。";
		Segment segment = HanLP.newSegment().enableNameRecognize(true);
		List<Term> termList = segment.seg(input);
	    System.out.println(termList);
	}
}
