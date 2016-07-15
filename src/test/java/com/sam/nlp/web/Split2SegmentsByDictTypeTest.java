package com.sam.nlp.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
/**
 * 文本分段测试
 * 
 * @author  lfs
 * @version  [版本号, 2016年7月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Split2SegmentsByDictTypeTest
{
    private String text = "我们再看其他城市，北京、深圳已经收紧了楼市调控政策，合肥、厦门分别从7月1日、7月15日起提高首付比例“降杠杆”控风险，预计南京、苏州、武汉、、杭州、东莞、佛山等上述核心二三线城市也会在三季度跟进提高首付“降杠杆”的信贷政策。由此可见，表面上来看，去年下半年以来，上述核心一二线城市商品住宅市场出现量价齐升，“地王”频出也推动房价继续上涨，但是，由于首付比例的提高导致的杠杆率降低、政策面预期收紧与市场调整的来临，上述城市楼市火热的背后仍然存在潜在的金融风险。";
    /**
     * 1、添加自定义词典
     * 2、依存句法分析，将关键指标及其修饰词、词性、词的位置获取
     * 3、结构化，如 楼市调控政策。动作（词性）：收紧。区域（地名）（词性）：北京、深圳
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void testsplitSetence2Segments() {
        
        try
        {
            Split2SegmentsByDictType ss = new Split2SegmentsByDictType();
            ss.addDcit("社会科学/房地产", null, null);
            final List<String> wordsWanted = new ArrayList<String>();
            final char[] charArray = text.toCharArray();
            //分词
            CustomDictionary.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
            {
                public void hit(int begin, int end, CoreDictionary.Attribute value)
                {
                    String word = new String(charArray, begin, end - begin);
                    System.out.printf("[%d:%d]=%s, %s\n", begin, end, word, value);
                    wordsWanted.add(word);
                }
            });
            //依存句法分析
            CoNLLSentence sentence = HanLP.parseDependency(text);
            //System.out.println(sentence);
            // 可以方便地遍历它
            for (CoNLLWord word : sentence)
            {
                if (wordsWanted.contains(word.HEAD.LEMMA)) {
                    
                }
                System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 自动摘要
     */
    @Test
    public void TextRankSentenceTest() {
        List<String> sentenceList = HanLP.extractSummary(text, 3);
        System.out.println(sentenceList);
    }
    /**
     * 短语提取
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void PhraseExtractorTest() {
        List<String> phraseList = HanLP.extractPhrase(text, 10);
        System.out.println(phraseList);
    }
    
    /**
     * 依存句法分析
     */
    @Test
    public void NeuralNetworkDependencyParserTest() {
        CoNLLSentence sentence = HanLP.parseDependency(text);
        System.out.println(sentence);
        // 可以方便地遍历它
        for (CoNLLWord word : sentence)
        {
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }
        System.out.println("-----------------------------------也可以直接拿到数组，任意顺序或逆序遍历------------------------------");
        // 也可以直接拿到数组，任意顺序或逆序遍历
        CoNLLWord[] wordArray = sentence.getWordArray();
        for (int i = wordArray.length - 1; i >= 0; i--)
        {
            CoNLLWord word = wordArray[i];
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }
        System.out.println("-----------------------------------还可以直接遍历子树，从某棵子树的某个节点一路遍历到虚根------------------------------");
        // 还可以直接遍历子树，从某棵子树的某个节点一路遍历到虚根
        CoNLLWord head = wordArray[12];
        while ((head = head.HEAD) != null)
        {
            if (head == CoNLLWord.ROOT) System.out.println(head.LEMMA);
            else System.out.printf("%s --(%s)--> ", head.LEMMA, head.DEPREL);
        }
    }
}
