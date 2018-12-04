//package com.zuoyue.weiyang.lucene;
//
//import com.zuoyue.weiyang.bean.MyPageInfo;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.cjk.CJKAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.analysis.util.CharArraySet;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.*;
//import org.apache.lucene.store.RAMDirectory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ResUnitSearcher {
//
//    @Autowired
//    private ResUnitService resUnitService;
//
//    public static ResUnitSearcher instance;
//
//    private static RAMDirectory ramDirectory;
//    private static IndexWriter ramWriter;
//
//    @PostConstruct
//    public void init() {
//        instance = this;
//        reCreateIndex();
//    }
//
//    public void reCreateIndex() {
//        try {
//            ramDirectory = new RAMDirectory();
//            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new CJKAnalyzer(CharArraySet.EMPTY_SET)); // 中文分词器
//            ramWriter = new IndexWriter(ramDirectory, indexWriterConfig);
//            List<ResUnit> list = instance.resUnitService.list(null);
//            for (ResUnit resUnit : list) {
////                System.out.println("part: " + new ObjectMapper().writeValueAsString(part));
//                ramWriter.addDocument(toDocument(resUnit));
//            }
//            ramWriter.commit();
//            System.out.println("-----Lucene初始化-----");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Document toDocument(ResUnit resUnit) {
//        Document doc = new Document();
//        doc.add(new StringField("all_flag", "all", Field.Store.YES));
//
//        doc.add(new StringField("id", String.valueOf(resUnit.getId()), Field.Store.YES));
//
//        String name = resUnit.getName();
//        doc.add(new Field("name", name, TextField.TYPE_STORED));
//        doc.add(new Field("single_name", KeywordUtils.getSingleChinese(name), TextField.TYPE_STORED));
//
//        String str = KeywordUtils.getStr(name);
//        doc.add(new Field("chinese_key", KeywordUtils.getChinese(str), TextField.TYPE_STORED));
//        doc.add(new Field("pinyin", KeywordUtils.getPinyin(str), TextField.TYPE_STORED));
//        doc.add(new Field("short_pinyin", KeywordUtils.getShortPinyin(str), TextField.TYPE_STORED));
//
//        Field rePinyinField = new Field("re_pinyin", KeywordUtils.getRePinyin(str), TextField.TYPE_STORED);
//        rePinyinField.setBoost(0.5f);
//        doc.add(rePinyinField);
//
//        doc.add(new Field("small_pic", resUnit.getSmallPic(), TextField.TYPE_STORED));
//        doc.add(new Field("gather_time", resUnit.getGatheringTime(), TextField.TYPE_STORED));
//        doc.add(new Field("phone", resUnit.getPhone(), TextField.TYPE_STORED));
//        doc.add(new Field("area", resUnit.getArea(), TextField.TYPE_STORED));
//        doc.add(new Field("gather_place", resUnit.getGatheringPlace(), TextField.TYPE_STORED));
//        return doc;
//    }
//
//    // 添加索引
//    public synchronized void addDocument(ResUnit resUnit) throws IOException {
//        ramWriter.addDocument(toDocument(resUnit));
//        ramWriter.commit();
//    }
//
//    // 批量添加索引
//    public synchronized void addDocuments(List<ResUnit> resUnits) throws IOException {
//        for (ResUnit resUnit : resUnits) {
//            ramWriter.addDocument(toDocument(resUnit));
//        }
//        ramWriter.commit();
//    }
//
//    // 更新索引
//    public void updateDocument(ResUnit resUnit) throws IOException{
//        Term term = new Term("id",String.valueOf(resUnit.getId()));
//        ramWriter.updateDocument(term, toDocument(resUnit));
//        ramWriter.commit();
//    }
//
//    // 批量更新索引
//    public void updateDocuments(List<ResUnit> resUnits) throws IOException{
//        for (ResUnit resUnit : resUnits) {
//            Term term = new Term("id",String.valueOf(resUnit.getId()));
//            ramWriter.updateDocument(term, toDocument(resUnit));
//        }
//        ramWriter.commit();
//    }
//
//    // 删除索引
//    public synchronized void deleteDocument(Long id) throws IOException {
//        Term term = new Term("id", String.valueOf(id));
//        ramWriter.deleteDocuments(term);
//        ramWriter.commit();
//    }
//
//    private Sort getSort() {
//        //按评分排序，升序
//        SortField sf1 = new SortField(null, SortField.Type.SCORE, false);
//        //按照id排序，降序
//        SortField sf2 = new SortField(null, SortField.Type.DOC, true);
//
//        return new Sort(sf1, sf2);
//    }
//
//    public Query getPartBuilder(String keyword, String sort) throws ParseException {
//        BooleanQuery.Builder builder = new BooleanQuery.Builder();
//        Analyzer analyzer = new CJKAnalyzer(CharArraySet.EMPTY_SET);
//
//        String str = KeywordUtils.getStr(keyword);
//        if (StringUtils.isEmpty(keyword) && StringUtils.isEmpty(sort)) {
//            MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "all_flag" }, analyzer);
//            builder.add(parser.parse("all"), BooleanClause.Occur.MUST);
//            System.out.println("keyword:" + keyword + ", builder: " + builder.build().toString());
//            return builder.build();
//        }
//
//        BooleanQuery.Builder chineseBuilder = new BooleanQuery.Builder();
//        BooleanQuery.Builder keywordBuilder = new BooleanQuery.Builder();
//        BooleanQuery.Builder pinyinBuilder = new BooleanQuery.Builder();
//
//        if (str.length() > 0) {
//            String allStr = keyword.replaceAll("[\\s\\'\\,\\*]+", " ");
//            if (allStr.length() > 0) {
//                {
//                    QueryParser allParser = new QueryParser("name", analyzer);
//                    BooleanQuery.Builder allBuilder = new BooleanQuery.Builder();
//                    allBuilder.add(allParser.parse(allStr), BooleanClause.Occur.SHOULD);
//                    chineseBuilder.add(allBuilder.build(), BooleanClause.Occur.SHOULD);
//                }
//                {
//                    BooleanQuery.Builder strBuider = new BooleanQuery.Builder();
//                    for (String key : KeywordUtils.getSingleChinese(allStr).split(" ")) {
//                        QueryParser strParser = new QueryParser("single_name", new StandardAnalyzer(CharArraySet.EMPTY_SET));
//                        strBuider.add(strParser.parse(key), BooleanClause.Occur.MUST);
//                    }
//                    chineseBuilder.add(strBuider.build(), BooleanClause.Occur.MUST);
//                }
//                builder.add(chineseBuilder.build(), BooleanClause.Occur.SHOULD);
//            }
//
//            String chineseKey = KeywordUtils.getChinese(str);
//            if (chineseKey.length() > 0) {
//                QueryParser nameParser = new QueryParser("chinese_key", analyzer);
//                keywordBuilder.add(nameParser.parse(chineseKey), BooleanClause.Occur.MUST);
//            }
//
//            QueryParser pinyinParser = new QueryParser("pinyin", analyzer);
//            pinyinBuilder.add(pinyinParser.parse(KeywordUtils.getPinyin(str) + "*"), BooleanClause.Occur.SHOULD);
//
//            QueryParser repinyinParser = new QueryParser("re_pinyin", analyzer);
//            pinyinBuilder.add(repinyinParser.parse(KeywordUtils.getRePinyin(str) + "*"), BooleanClause.Occur.SHOULD);
//
//            QueryParser shortpinyinParser = new QueryParser("short_pinyin", analyzer);
//            pinyinBuilder.add(shortpinyinParser.parse(KeywordUtils.getShortPinyin(str) + "*"), BooleanClause.Occur.SHOULD);
//
//            keywordBuilder.add(pinyinBuilder.build(), BooleanClause.Occur.MUST);
//            builder.add(keywordBuilder.build(), BooleanClause.Occur.SHOULD);
//        }
//
//        if (sort != null && sort.length() > 0) {
//            QueryParser sortParser = new QueryParser("sort_id", analyzer);
//            builder.add(sortParser.parse(sort), BooleanClause.Occur.MUST);
//        }
//
//        System.out.println("keyword:" + keyword + ", builder: " + builder.build().toString());
//
//        return builder.build();
//    }
//
//    public Query getPartBuilder(String keyword) throws ParseException {
//        return getPartBuilder(keyword, null);
//    }
//
//    /**
//     * Lucene分页查询
//     * @param searcher
//     * @param query
//     * @param pageNum
//     * @param pageSize
//     * @return
//     * @throws IOException
//     */
//    public MyPageInfo<ResUnit> pageQuery(IndexSearcher searcher, Query query, int pageNum, int pageSize) throws IOException {
//        MyPageInfo<ResUnit> page = new MyPageInfo<>();
//        int setOff = (pageNum - 1) * pageSize;
//        List<ResUnit> list = new ArrayList<>();
//
//        ScoreDoc[] records = searchTotal(searcher, query);
//        int totalRecord = records.length;
//        // 设置总记录数
//        page.setTotal(totalRecord);
//        page.setPages((totalRecord + 1) / pageSize);
//
//        for (int i = setOff; i < (setOff + pageSize) && i < records.length; i++) {
//            int docID = records[i].doc;
//            Document doc = searcher.doc(docID);
//            list.add(new ResUnit()
//                    .setId(Long.valueOf(doc.get("id")))
//                    .setName(doc.get("name"))
//                    .setSmallPic(doc.get("small_pic"))
//                    .setGatheringTime(doc.get("gathering_time"))
//                    .setPhone(doc.get("phone"))
//                    .setArea(doc.get("area"))
//                    .setGatheringPlace(doc.get("gathering_place"))
//            );
//        }
//        page.setList(list);
//        searcher.getIndexReader().close();
//
//        return page;
//    }
//
//    public ScoreDoc[] searchTotal(IndexSearcher searcher, Query query) throws IOException {
//        TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE, getSort());
//        if (topDocs == null || topDocs.scoreDocs == null || topDocs.scoreDocs.length == 0) {
//            return new ScoreDoc[0];
//        }
//        ScoreDoc[] docs = topDocs.scoreDocs;
//        return docs;
//    }
//
//    public MyPageInfo<ResUnit> search(String keyword, int pageNum, int pageSize) throws ParseException, IOException {
//        Query query = getPartBuilder(keyword);
//        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(ramDirectory));
//        return pageQuery(indexSearcher ,query ,pageNum, pageSize);
//    }
//}
