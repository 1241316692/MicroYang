//package com.boyue.popularsciencetour.lucene;
//
//import com.boyue.bean.GoodsOnePiece;
//import com.boyue.bean.GoodsPart;
//import com.boyue.dao.GoodsOnePieceDao;
//import com.boyue.dao.GoodsPartDao;
//import com.boyue.utils.KeywordUtils;
//import org.apache.http.util.TextUtils;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.cjk.CJKAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.analysis.util.CharArraySet;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.*;
//import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.*;
//import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.store.IOContext;
//import org.apache.lucene.store.RAMDirectory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * Created by cherry on 2018/2/26.
// */
//@Component
//public class LuceneUtil {
//
//    @Autowired
//    GoodsPartDao goodsPartDao;
//
//    @Autowired
//    GoodsOnePieceDao goodsOnePieceDao;
//
//    public static final String INDEXPATH = "D:\\productManage\\lucene\\part";
//    public static final String INDEXPATH_GOODS = "D:\\productManage\\lucene\\goods";
//    private static RAMDirectory ramDirectory;
//    private static IndexWriter ramWriter;
//    private static RAMDirectory ramDirectoryForGoods;
//    private static IndexWriter ramWriterForGoods;
//
////    static {
////        try {
////            File dir = new File(INDEXPATH);
////            if (!dir.exists()) dir.mkdirs();
////
////            FSDirectory fsDirectory = FSDirectory.open(Paths.get(INDEXPATH));
////            ramDirectory = new RAMDirectory(fsDirectory, IOContext.READONCE);
////            fsDirectory.close();
////
////            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer()); // 中文分词器
////            indexWriterConfig.setIndexDeletionPolicy(new SnapshotDeletionPolicy(new KeepOnlyLastCommitDeletionPolicy()));
////
////            ramWriter = new IndexWriter(ramDirectory, indexWriterConfig);
////            System.out.println("-----Lucene初始化-----");
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//
//    public static LuceneUtil luceneUtil;
//
//    @PostConstruct
//    public void init() {
//        luceneUtil = this;
//        reCreateIndex();
//        initRam();
//
////        reCreateIndexForGoods();
//        initRamForGoods();
//    }
//
//    private void initRam() {
//        try {
//            File dir = new File(INDEXPATH);
//            if (!dir.exists()) dir.mkdirs();
//
//            FSDirectory fsDirectory = FSDirectory.open(Paths.get(INDEXPATH));
//            ramDirectory = new RAMDirectory(fsDirectory, IOContext.READONCE);
//            fsDirectory.close();
//
//            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new CJKAnalyzer(CharArraySet.EMPTY_SET)); // 中文分词器
//            indexWriterConfig.setIndexDeletionPolicy(new SnapshotDeletionPolicy(new KeepOnlyLastCommitDeletionPolicy()));
//
//            ramWriter = new IndexWriter(ramDirectory, indexWriterConfig);
//            System.out.println("-----Lucene初始化-----");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 于磁盘创建索引
//    public void reCreateIndex() {
//        System.out.println("-----创建配件索引-----");
//        Long startTime = System.currentTimeMillis();
//        try {
//            Path path = Paths.get(INDEXPATH);
//            // 删除原有索引文件
//            for (File file : path.toFile().listFiles()) {
//                file.delete();
//            }
//            FSDirectory fsDirectory = FSDirectory.open(path);
////            Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET); // 中文分词器
//            Analyzer analyzer = new CJKAnalyzer(CharArraySet.EMPTY_SET);
//            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//            IndexWriter writer = new IndexWriter(fsDirectory, indexWriterConfig);
//            List<GoodsPart> goodsParts = luceneUtil.goodsPartDao.findAll();
//            for (GoodsPart part : goodsParts) {
////                System.out.println("part: " + new ObjectMapper().writeValueAsString(part));
//                writer.addDocument(toDocument(part));
//            }
//            writer.close();
//            System.out.println("-----创建配件索引成功, " + (System.currentTimeMillis() - startTime) + " ms-----");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initRamForGoods() {
////        try {
////            FSDirectory fsDirectory = FSDirectory.open(Paths.get(INDEXPATH_GOODS));
////            ramDirectoryForGoods = new RAMDirectory(fsDirectory, IOContext.READONCE);
////            fsDirectory.close();
////
////            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new CJKAnalyzer(CharArraySet.EMPTY_SET)); // 中文分词器
////            indexWriterConfig.setIndexDeletionPolicy(new SnapshotDeletionPolicy(new KeepOnlyLastCommitDeletionPolicy()));
////
////            ramWriterForGoods = new IndexWriter(ramDirectoryForGoods, indexWriterConfig);
////            System.out.println("-----Lucene初始化-----");
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        try {
//            ramDirectoryForGoods = new RAMDirectory();
//            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new CJKAnalyzer(CharArraySet.EMPTY_SET)); // 中文分词器
//            ramWriterForGoods = new IndexWriter(ramDirectoryForGoods, indexWriterConfig);
//            List<GoodsOnePiece> goodsList = luceneUtil.goodsOnePieceDao.findAll();
//            for (GoodsOnePiece goods : goodsList) {
////                System.out.println("part: " + new ObjectMapper().writeValueAsString(part));
//                ramWriterForGoods.addDocument(toDocumentForGoods(goods));
//            }
//            ramWriterForGoods.commit();
//            System.out.println("-----Lucene初始化-----");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    // 于磁盘创建索引
//    public void reCreateIndexForGoods() {
//        System.out.println("-----创建配件索引-----");
//        Long startTime = System.currentTimeMillis();
//        try {
//            Path path = Paths.get(INDEXPATH_GOODS);
//            // 删除原有索引文件
//            for (File file : path.toFile().listFiles()) {
//                file.delete();
//            }
//            FSDirectory fsDirectory = FSDirectory.open(path);
////            Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET); // 中文分词器
//            Analyzer analyzer = new CJKAnalyzer(CharArraySet.EMPTY_SET);
//            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//            IndexWriter writer = new IndexWriter(fsDirectory, indexWriterConfig);
//            List<GoodsOnePiece> goodsList = luceneUtil.goodsOnePieceDao.findAll();
//            for (GoodsOnePiece goods : goodsList) {
////                System.out.println("part: " + new ObjectMapper().writeValueAsString(part));
//                writer.addDocument(toDocumentForGoods(goods));
//            }
//            writer.close();
//            System.out.println("-----创建配件索引成功, " + (System.currentTimeMillis() - startTime) + " ms-----");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 实体part对象转document索引对象
//    public Document toDocument(GoodsPart part) {
//        String name = part.getPartName();
//
//        Document doc = new Document();
//        doc.add(new StringField("all_flag", "all", Field.Store.YES));
//
//        doc.add(new StringField("id", String.valueOf(part.getId()), Field.Store.YES));
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
//        doc.add(new Field("spec", part.getSpec(), TextField.TYPE_STORED));
//        doc.add(new Field("color", part.getColor(), TextField.TYPE_STORED));
//        doc.add(new Field("unit", part.getUnit(), TextField.TYPE_STORED));
//
//        return doc;
//    }
//
//    // 添加索引
//    public synchronized void addDocument(GoodsPart part) throws IOException {
//        ramWriter.addDocument(toDocument(part));
//        ramWriter.commit();
//    }
//
//    // 批量添加索引
//    public synchronized void addDocuments(List<GoodsPart>  parts) throws IOException {
//        for (GoodsPart part : parts) {
//            ramWriter.addDocument(toDocument(part));
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
//    // 更新索引
//    public void updateDocument(GoodsPart part) throws IOException{
//        Term term = new Term("id",String.valueOf(part.getId()));
//        ramWriter.updateDocument(term, toDocument(part));
//        ramWriter.commit();
//    }
//
//    // 批量更新索引
//    public void updateDocuments(List<GoodsPart> parts) throws IOException{
//        for (GoodsPart part : parts) {
//            Term term = new Term("id",String.valueOf(part.getId()));
//            ramWriter.updateDocument(term, toDocument(part));
//        }
//        ramWriter.commit();
//    }
//
//    // 实体goods对象转document索引对象
//    public Document toDocumentForGoods(GoodsOnePiece goods) {
//        String name = goods.getGoodsName();
//
//        Document doc = new Document();
//        doc.add(new StringField("all_flag", "all", Field.Store.YES));
//
//        doc.add(new StringField("id", String.valueOf(goods.getId()), Field.Store.YES));
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
//        doc.add(new Field("spec", goods.getSpec(), TextField.TYPE_STORED));
//        doc.add(new Field("color", goods.getColor(), TextField.TYPE_STORED));
//        doc.add(new Field("unit", goods.getUnit(), TextField.TYPE_STORED));
//
//        doc.add(new StringField("sort_id", String.valueOf(goods.getGoodsSortId()), Field.Store.YES));
//
//        return doc;
//    }
//
//    // 商品添加索引
//    public synchronized void addDocumentForGoods(GoodsOnePiece goods) throws IOException {
//        ramWriterForGoods.addDocument(toDocumentForGoods(goods));
//        ramWriterForGoods.commit();
//    }
//
//    // 商品批量添加索引
//    public synchronized void addDocumentsForGoods(List<GoodsOnePiece>  goods) throws IOException {
//        for (GoodsOnePiece g : goods) {
//            ramWriterForGoods.addDocument(toDocumentForGoods(g));
//        }
//        ramWriterForGoods.commit();
//    }
//
//    // 商品删除索引
//    public synchronized void deleteDocumentForGoods(Long id) throws IOException {
//        Term term = new Term("id", String.valueOf(id));
//        ramWriterForGoods.deleteDocuments(term);
//        ramWriterForGoods.commit();
//    }
//
//    // 商品更新索引
//    public void updateDocumentForGoods(GoodsOnePiece goods) throws IOException{
//        Term term = new Term("id",String.valueOf(goods.getId()));
//        ramWriterForGoods.updateDocument(term, toDocumentForGoods(goods));
//        ramWriterForGoods.commit();
//    }
//
//    // 商品批量更新索引
//    public void updateDocumentsForGoods(List<GoodsOnePiece> goods) throws IOException{
//        for (GoodsOnePiece g : goods) {
//            Term term = new Term("id",String.valueOf(g.getId()));
//            ramWriterForGoods.updateDocument(term, toDocumentForGoods(g));
//        }
//        ramWriterForGoods.commit();
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
//    // 搜索索引
//    public List<GoodsPart> search(String keyword, int limit) throws IOException, ParseException, InvalidTokenOffsetsException {
//        List<GoodsPart> list = new ArrayList<>();
//        if (keyword.length() == 0) return list;
//
//        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(ramDirectory));
//
//        TopDocs hits = indexSearcher.search(getPartBuilder(keyword), limit, getSort());
//        for (ScoreDoc scoreDoc : hits.scoreDocs) {
//            Document doc = indexSearcher.doc(scoreDoc.doc);
//            GoodsPart part = new GoodsPart();
//            part.setId(Long.valueOf(doc.get("id")));
//            part.setPartName(doc.get("name"));
//            part.setSpec(doc.get("spec"));
//            part.setColor(doc.get("color"));
//            part.setUnit(doc.get("unit"));
//
//            list.add(part);
//        }
//
//        return list;
//    }
//
//    // 搜索索引
//    public List<GoodsOnePiece> searchForGoods(String keyword, int limit) throws IOException, ParseException, InvalidTokenOffsetsException {
//        List<GoodsOnePiece> list = new ArrayList<>();
//        if (keyword.length() == 0) return list;
//
//        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(ramDirectory));
//
//        TopDocs hits = indexSearcher.search(getPartBuilder(keyword), limit, getSort());
//        for (ScoreDoc scoreDoc : hits.scoreDocs) {
//            Document doc = indexSearcher.doc(scoreDoc.doc);
//            GoodsOnePiece goods = new GoodsOnePiece();
//            goods.setId(Long.valueOf(doc.get("id")));
//            goods.setGoodsName(doc.get("name"));
//            goods.setSpec(doc.get("spec"));
//            goods.setColor(doc.get("color"));
//            goods.setUnit(doc.get("unit"));
//
//            goods.setGoodsSortId(Long.valueOf(doc.get("sort_id")));
//
//            list.add(goods);
//        }
//
//        return list;
//    }
//
//    // 同步索引至磁盘
//    public void indexSync() {
//        IndexWriterConfig config = null;
//        SnapshotDeletionPolicy snapshotDeletionPolicy = null;
//        IndexCommit indexCommit = null;
//
//        try {
//            config = (IndexWriterConfig) ramWriter.getConfig();
//            snapshotDeletionPolicy = (SnapshotDeletionPolicy) config.getIndexDeletionPolicy();
//            indexCommit = snapshotDeletionPolicy.snapshot();
//            config.setIndexCommit(indexCommit);
//            Collection<String> fileNames = indexCommit.getFileNames();
//            Path toPath = Paths.get(INDEXPATH);
//            Directory toDir = FSDirectory.open(toPath);
//            // 删除所有原有索引文件
//            for (File file : toPath.toFile().listFiles()) {
//                file.delete();
//            }
//            // 从randir复制新索引文件至磁盘
//            for (String fileName : fileNames) {
//                toDir.copyFrom(ramDirectory, fileName, fileName, IOContext.DEFAULT);
//            }
//            toDir.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("-----索引同步完成------");
//    }
//
//    public Query getPartBuilder(String keyword, String sort) throws ParseException {
//        BooleanQuery.Builder builder = new BooleanQuery.Builder();
//        Analyzer analyzer = new CJKAnalyzer(CharArraySet.EMPTY_SET);
//
//        String str = KeywordUtils.getStr(keyword);
//        if (TextUtils.isEmpty(keyword) && TextUtils.isEmpty(sort)) {
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
////                    String filterStr = allBuilder.build().toString().replaceAll("^\\(|\\)$", "");
////                    System.out.println("filterStr: " + filterStr);
////
////                    BooleanQuery.Builder strBuider = new BooleanQuery.Builder();
////                    for (String key : filterStr.split(" ")) {
////                        QueryParser strParser = new QueryParser("name", analyzer);
////                        strBuider.add(strParser.parse(key + "*"), BooleanClause.Occur.MUST);
////                    }
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
//     * @param currentPage
//     * @param pageSize
//     * @return
//     * @throws IOException
//     */
//    public Page<Document> pageQuery(IndexSearcher searcher, Query query, int currentPage, int pageSize) throws IOException {
//        Page<Document> page = new Page<Document>(currentPage, pageSize);
//        int setOff = (currentPage - 1) * pageSize;
//        List<Document> docList = new ArrayList<Document>();
//
////        if (query == null) {
////            IndexReader reader = searcher.getIndexReader();//读取目录
////            int count = reader.maxDoc();//所有文档数
////            for (int i = setOff; i < (setOff + pageSize) && i < count; i++){
////                Document doc = searcher.doc((count - 1) - i);
////                docList.add(doc);
////            }
////            page.setTotalRecord(count);
////            page.setTotalPage((count + 1) / pageSize);
////            page.setItems(docList);
////            reader.close();
////            return page;
////        }
//
//        ScoreDoc[] records = searchTotalRecord(searcher, query);
//        int totalRecord = records.length;
//        // 设置总记录数
//        page.setTotalRecord(totalRecord);
//        page.setTotalPage((totalRecord + 1) / pageSize);
////        TopDocs topDocs = searcher.searchAfter(page.getAfterDoc(), query, page.getPageSize(), getSort());
////        ScoreDoc[] docs = topDocs.scoreDocs;
//        for (int i = setOff; i < (setOff + pageSize) && i < records.length; i++) {
//            int docID = records[i].doc;
//            Document document = searcher.doc(docID);
//            docList.add(document);
//        }
//        page.setItems(docList);
//        searcher.getIndexReader().close();
//
//        return page;
//    }
//
//    public ScoreDoc[] searchTotalRecord(IndexSearcher searcher, Query query) throws IOException {
//        TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE, getSort());
//        if (topDocs == null || topDocs.scoreDocs == null || topDocs.scoreDocs.length == 0) {
//            return new ScoreDoc[0];
//        }
//        ScoreDoc[] docs = topDocs.scoreDocs;
//        return docs;
//    }
//
//    public Page<Document> searchPart(String keyword, int currentPage, int pageSize) throws ParseException, IOException {
//        Query query = getPartBuilder(keyword);
//        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(ramDirectory));
//        return pageQuery(indexSearcher ,query ,currentPage, pageSize);
//    }
//
//    public Page<Document> searchGoods(String keyword, List<Long> longs, int currentPage, int pageSize) throws ParseException, IOException {
//        String sortIds = "";
//        if (longs != null && longs.size() > 0) {
//            for (Long id : longs) {
//                sortIds += id + " ";
//            }
//            sortIds = sortIds.trim();
//        }
//
//        Query query = getPartBuilder(keyword, sortIds);
//        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(ramWriterForGoods, false));
//        return pageQuery(indexSearcher ,query ,currentPage, pageSize);
//    }
//
//}
