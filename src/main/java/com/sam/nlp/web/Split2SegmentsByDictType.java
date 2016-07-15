package com.sam.nlp.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;

/**
 * 根据字典类型把句子分成段，动态加载词典，建立词典名称-词典txt文件的映射，研究hanlp动态加载词典
 * @author lenovo-pc
 * 2016年7月10日
 */
public class Split2SegmentsByDictType {

    public static final String CHARSET_UTF8 = "UTF-8";
	public void addDcit(String dictType, String defaultNature, Integer defaultFrequency) {
	    String filePath = "F:/soft/java/unstructure/nlp/词库/搜狗/";
	    filePath += dictType;
	    File fileDirectory = new File(filePath);
	    File[] listFiles = fileDirectory.listFiles();//目录下所有的词典
	    for (File file : listFiles) {
	        List<String> dicts = new ArrayList<String>();
	        BufferedReader br = null;
	        try
            {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), CHARSET_UTF8));
                String line = null;
                while ((line = br.readLine()) != null) {
                    dicts.add(line);
                }
                if (dicts.size() > 0) {
                    //添加自定义词典
                    for (String dict : dicts) {
                        String[] split = dict.split(" ");
                        if (split.length == 1) {
                            String natureWithFrequency = null;
                            if (defaultNature != null) {
                                if (defaultFrequency != null) {
                                    natureWithFrequency =  defaultNature + " " + defaultFrequency;
                                } else {
                                    natureWithFrequency = defaultNature;
                                }
                            }
                            CustomDictionary.add(dict, natureWithFrequency);
                        } else if (split.length == 2) {
                            CustomDictionary.add(dict, split[1]);
                        } else if (split.length == 3) {
                            CustomDictionary.add(dict, split[1] + " " + split[2]);
                        }
                    }
                }
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
	        finally {
	            if (br != null) {
	                try
                    {
                        br.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
	            }
	        }
	    }
	}
	public void splitSetence2Segments(String text) {
	    //String text = "我们再看其他城市，北京、深圳已经收紧了楼市调控政策，合肥、厦门分别从7月1日、7月15日起提高首付比例“降杠杆”控风险，预计南京、苏州、武汉、、杭州、东莞、佛山等上述核心二三线城市也会在三季度跟进提高首付“降杠杆”的信贷政策。由此可见，表面上来看，去年下半年以来，上述核心一二线城市商品住宅市场出现量价齐升，“地王”频出也推动房价继续上涨，但是，由于首付比例的提高导致的杠杆率降低、政策面预期收紧与市场调整的来临，上述城市楼市火热的背后仍然存在潜在的金融风险。";
	    addDcit("社会科学/房地产", null, null);
	    final char[] charArray = text.toCharArray();
        CustomDictionary.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
        {
            public void hit(int begin, int end, CoreDictionary.Attribute value)
            {
                System.out.printf("[%d:%d]=%s, %s\n", begin, end, new String(charArray, begin, end - begin), value);
            }
        });
	    
	}
}