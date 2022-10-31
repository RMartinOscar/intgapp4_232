/*     */ package com.hp.ilo2.intgapp;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class locinfo
/*     */ {
/*     */   private DocumentBuilderFactory dbf;
/*     */   private DocumentBuilder db;
/*     */   private Document document;
/*     */   private File file;
/*     */   public static int UID;
/*     */   private intgapp ParentApp;
/*     */   private String localLocStrFile;
/*  48 */   private String lstrVersion = "0001";
/*     */   
/*  50 */   public String rcErrMessage = "";
/*     */   
/*     */   public static final int MENUSTR_1001 = 4097;
/*     */   
/*     */   public static final int MENUSTR_1002 = 4098;
/*     */   
/*     */   public static final int MENUSTR_1003 = 4099;
/*     */   
/*     */   public static final int MENUSTR_1004 = 4100;
/*     */   
/*     */   public static final int MENUSTR_1005 = 4101;
/*     */   
/*     */   public static final int MENUSTR_1006 = 4102;
/*     */   
/*     */   public static final int MENUSTR_1007 = 4103;
/*     */   
/*     */   public static final int MENUSTR_1008 = 4104;
/*     */   
/*     */   public static final int MENUSTR_1009 = 4105;
/*     */   
/*     */   public static final int MENUSTR_100A = 4106;
/*     */   
/*     */   public static final int MENUSTR_100B = 4107;
/*     */   
/*     */   public static final int MENUSTR_100C = 4108;
/*     */   
/*     */   public static final int MENUSTR_100D = 4109;
/*     */   
/*     */   public static final int MENUSTR_100E = 4110;
/*     */   public static final int MENUSTR_100F = 4111;
/*     */   public static final int MENUSTR_1010 = 4112;
/*     */   public static final int MENUSTR_1011 = 4113;
/*     */   public static final int MENUSTR_1012 = 4114;
/*     */   public static final int MENUSTR_1013 = 4115;
/*     */   public static final int MENUSTR_1014 = 4116;
/*     */   public static final int MENUSTR_1015 = 4117;
/*     */   public static final int MENUSTR_1016 = 4118;
/*     */   public static final int MENUSTR_1017 = 4119;
/*     */   public static final int MENUSTR_1018 = 4120;
/*     */   public static final int MENUSTR_1019 = 4121;
/*     */   public static final int MENUSTR_101A = 4122;
/*     */   public static final int MENUSTR_101B = 4123;
/*     */   public static final int MENUSTR_101C = 4124;
/*     */   public static final int MENUSTR_101D = 4125;
/*     */   public static final int MENUSTR_101E = 4126;
/*     */   public static final int MENUSTR_101F = 4127;
/*     */   public static final int MENUSTR_1020 = 4128;
/*     */   public static final int MENUSTR_1021 = 4129;
/*     */   public static final int MENUSTR_1022 = 4130;
/*     */   public static final int MENUSTR_1023 = 4131;
/*     */   public static final int MENUSTR_1024 = 4132;
/*     */   public static final int MENUSTR_1025 = 4133;
/*     */   public static final int MENUSTR_1026 = 4134;
/*     */   public static final int MENUSTR_1027 = 4135;
/*     */   public static final int MENUSTR_1028 = 4136;
/*     */   public static final int MENUSTR_1029 = 4137;
/*     */   public static final int DIALOGSTR_2001 = 8193;
/*     */   public static final int DIALOGSTR_2002 = 8194;
/*     */   public static final int DIALOGSTR_2003 = 8195;
/*     */   public static final int DIALOGSTR_2004 = 8196;
/*     */   public static final int DIALOGSTR_2005 = 8197;
/*     */   public static final int DIALOGSTR_2006 = 8198;
/*     */   public static final int DIALOGSTR_2007 = 8199;
/*     */   public static final int DIALOGSTR_2008 = 8200;
/*     */   public static final int DIALOGSTR_2009 = 8201;
/*     */   public static final int DIALOGSTR_200a = 8202;
/*     */   public static final int DIALOGSTR_200b = 8203;
/*     */   public static final int DIALOGSTR_200c = 8204;
/*     */   public static final int DIALOGSTR_200d = 8205;
/*     */   public static final int DIALOGSTR_200e = 8206;
/*     */   public static final int DIALOGSTR_200f = 8207;
/*     */   public static final int DIALOGSTR_2010 = 8208;
/*     */   public static final int DIALOGSTR_2011 = 8209;
/*     */   public static final int DIALOGSTR_2012 = 8210;
/*     */   public static final int DIALOGSTR_2013 = 8211;
/*     */   public static final int DIALOGSTR_2014 = 8212;
/*     */   public static final int DIALOGSTR_2015 = 8213;
/*     */   public static final int DIALOGSTR_2016 = 8214;
/*     */   public static final int DIALOGSTR_2017 = 8215;
/*     */   public static final int DIALOGSTR_2021 = 8225;
/*     */   public static final int DIALOGSTR_2022 = 8226;
/*     */   public static final int DIALOGSTR_2023 = 8227;
/*     */   public static final int DIALOGSTR_2024 = 8228;
/*     */   public static final int DIALOGSTR_2025 = 8229;
/*     */   public static final int DIALOGSTR_2026 = 8230;
/*     */   public static final int DIALOGSTR_2027 = 8231;
/*     */   public static final int DIALOGSTR_2028 = 8232;
/*     */   public static final int DIALOGSTR_2029 = 8233;
/*     */   public static final int DIALOGSTR_202a = 8234;
/*     */   public static final int DIALOGSTR_202b = 8235;
/*     */   public static final int DIALOGSTR_202c = 8236;
/*     */   public static final int DIALOGSTR_202d = 8237;
/*     */   public static final int DIALOGSTR_202e = 8238;
/*     */   public static final int DIALOGSTR_202f = 8239;
/*     */   public static final int DIALOGSTR_2030 = 8240;
/*     */   public static final int DIALOGSTR_2031 = 8241;
/*     */   public static final int DIALOGSTR_2032 = 8242;
/*     */   public static final int DIALOGSTR_2033 = 8243;
/*     */   public static final int DIALOGSTR_2034 = 8244;
/*     */   public static final int DIALOGSTR_2035 = 8245;
/*     */   public static final int DIALOGSTR_2036 = 8246;
/*     */   public static final int DIALOGSTR_2037 = 8247;
/*     */   public static final int DIALOGSTR_2038 = 8248;
/*     */   public static final int DIALOGSTR_2039 = 8249;
/*     */   public static final int DIALOGSTR_203a = 8250;
/*     */   public static final int DIALOGSTR_203b = 8251;
/*     */   public static final int DIALOGSTR_203c = 8252;
/*     */   public static final int DIALOGSTR_203d = 8253;
/*     */   public static final int DIALOGSTR_203e = 8254;
/*     */   public static final int DIALOGSTR_203f = 8255;
/*     */   public static final int DIALOGSTR_2040 = 8256;
/*     */   public static final int DIALOGSTR_2041 = 8257;
/*     */   public static final int DIALOGSTR_2042 = 8258;
/*     */   public static final int DIALOGSTR_2043 = 8259;
/*     */   public static final int DIALOGSTR_2044 = 8260;
/*     */   public static final int DIALOGSTR_2045 = 8261;
/*     */   public static final int DIALOGSTR_2046 = 8262;
/*     */   public static final int DIALOGSTR_2047 = 8263;
/*     */   public static final int DIALOGSTR_2048 = 8264;
/*     */   public static final int DIALOGSTR_2049 = 8265;
/*     */   public static final int DIALOGSTR_205a = 8282;
/*     */   public static final int DIALOGSTR_205b = 8283;
/*     */   public static final int DIALOGSTR_205c = 8284;
/*     */   public static final int DIALOGSTR_205d = 8285;
/*     */   public static final int DIALOGSTR_205e = 8286;
/*     */   public static final int DIALOGSTR_205f = 8287;
/*     */   public static final int DIALOGSTR_2060 = 8288;
/*     */   public static final int DIALOGSTR_2061 = 8289;
/*     */   public static final int DIALOGSTR_2062 = 8290;
/*     */   public static final int DIALOGSTR_2063 = 8291;
/*     */   public static final int DIALOGSTR_2064 = 8292;
/*     */   public static final int DIALOGSTR_2065 = 8293;
/*     */   public static final int DIALOGSTR_2066 = 8294;
/*     */   public static final int DIALOGSTR_2067 = 8295;
/*     */   public static final int DIALOGSTR_2068 = 8296;
/*     */   public static final int DIALOGSTR_2069 = 8297;
/*     */   public static final int DIALOGSTR_206a = 8298;
/*     */   public static final int DIALOGSTR_206b = 8299;
/*     */   public static final int DIALOGSTR_206c = 8300;
/*     */   public static final int STATUSSTR_3001 = 12289;
/*     */   public static final int STATUSSTR_3002 = 12290;
/*     */   public static final int STATUSSTR_3003 = 12291;
/*     */   public static final int STATUSSTR_3004 = 12292;
/*     */   public static final int STATUSSTR_3005 = 12293;
/*     */   public static final int STATUSSTR_3006 = 12294;
/*     */   public static final int STATUSSTR_3007 = 12295;
/*     */   public static final int STATUSSTR_3008 = 12296;
/*     */   public static final int STATUSSTR_3009 = 12297;
/*     */   public static final int STATUSSTR_300a = 12298;
/*     */   public static final int STATUSSTR_300b = 12299;
/*     */   public static final int STATUSSTR_300c = 12300;
/*     */   public static final int STATUSSTR_300d = 12301;
/*     */   public static final int STATUSSTR_300e = 12302;
/*     */   public static final int STATUSSTR_300f = 12303;
/*     */   public static final int STATUSSTR_3010 = 12304;
/*     */   public static final int STATUSSTR_3011 = 12305;
/*     */   public static final int STATUSSTR_3012 = 12306;
/*     */   public static final int STATUSSTR_3013 = 12307;
/*     */   public static final int STATUSSTR_3014 = 12308;
/*     */   public static final int STATUSSTR_3100 = 12544;
/*     */   public static final int STATUSSTR_3101 = 12545;
/*     */   public static final int STATUSSTR_3102 = 12546;
/*     */   public static final int STATUSSTR_3103 = 12547;
/*     */   public static final int STATUSSTR_3104 = 12548;
/*     */   public static final int STATUSSTR_3105 = 12549;
/*     */   public static final int STATUSSTR_3106 = 12550;
/*     */   public static final int STATUSSTR_3107 = 12551;
/*     */   public static final int STATUSSTR_3108 = 12552;
/*     */   public static final int STATUSSTR_3109 = 12553;
/*     */   public static final int STATUSSTR_310a = 12554;
/*     */   public static final int STATUSSTR_310b = 12555;
/*     */   public static final int STATUSSTR_310c = 12556;
/*     */   public static final int STATUSSTR_310d = 12557;
/*     */   public static final int STATUSSTR_310e = 12558;
/*     */   public static final int STATUSSTR_310f = 12559;
/*     */   public static final int STATUSSTR_3110 = 12560;
/*     */   public static final int STATUSSTR_3111 = 12561;
/*     */   public static final int STATUSSTR_3112 = 12562;
/*     */   public static final int STATUSSTR_3113 = 12563;
/*     */   public static final int STATUSSTR_3114 = 12564;
/*     */   public static final int STATUSSTR_3115 = 12565;
/*     */   public static final int STATUSSTR_3116 = 12566;
/*     */   public static final int STATUSSTR_3117 = 12567;
/*     */   public static final int STATUSSTR_3118 = 12568;
/*     */   public static final int STATUSSTR_3119 = 12569;
/*     */   public static final int STATUSSTR_3120 = 12576;
/*     */   public static final int STATUSSTR_3121 = 12577;
/*     */   public static final int STATUSSTR_3122 = 12578;
/*     */   public static final int STATUSSTR_3123 = 12579;
/*     */   public static final int STATUSSTR_3124 = 12580;
/*     */   public static final int STATUSSTR_3125 = 12581;
/*     */   public static final int STATUSSTR_3126 = 12582;
/*     */   public static final int TOOLSTR_4001 = 16385;
/*     */   public static final int TOOLSTR_4002 = 16386;
/*     */   public static final int TOOLSTR_4003 = 16387;
/*     */   public static final int TOOLSTR_4004 = 16388;
/*     */   
/*     */   public locinfo(intgapp paramintgapp) {
/* 248 */     this.ParentApp = paramintgapp;
/* 249 */     this.dbf = null;
/* 250 */     this.db = null;
/* 251 */     this.document = null;
/* 252 */     this.file = null;
/* 253 */     this.lstrVersion = "0001";
/* 254 */     this.rcErrMessage = "";
/* 255 */     this.localLocStrFile = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retrieveLocStrings(boolean paramBoolean) {
/* 272 */     HttpURLConnection httpURLConnection = null;
/* 273 */     HttpsURLConnection httpsURLConnection = null;
/* 274 */     String str1 = null;
/* 275 */     String str2 = null;
/* 276 */     String str3 = null;
/* 277 */     Object object = null;
/* 278 */     String str4 = null;
/* 279 */     URL uRL = null;
/* 280 */     int i = 0;
/*     */ 
/*     */     
/* 283 */     String str5 = System.getProperty("java.io.tmpdir");
/* 284 */     String str6 = System.getProperty("os.name").toLowerCase();
/* 285 */     String str7 = System.getProperty("file.separator");
/* 286 */     boolean bool = false;
/* 287 */     String str8 = "com/hp/ilo2/intgapp/";
/* 288 */     String str9 = "jirc_strings";
/* 289 */     String str10 = ".xml";
/* 290 */     String str11 = this.ParentApp.getParameter("RCINFOLANG");
/* 291 */     String str12 = null;
/* 292 */     if (UID == 0)
/* 293 */       UID = hashCode(); 
/* 294 */     String str13 = Integer.toHexString(UID);
/*     */ 
/*     */     
/* 297 */     if (null != str11 && !str11.equalsIgnoreCase("")) {
/* 298 */       System.out.println("langStr received:" + str11);
/* 299 */       str12 = "lang/" + str11 + "/jirc_strings.xml";
/* 300 */       System.out.println("lolcalized xml file shoudl be:" + str12);
/*     */     } else {
/* 302 */       paramBoolean = false;
/*     */     } 
/*     */ 
/*     */     
/* 306 */     if (str5 == null) {
/* 307 */       str5 = str6.startsWith("windows") ? "C:\\TEMP" : "/tmp";
/*     */     }
/*     */     
/* 310 */     File file1 = new File(str5);
/* 311 */     if (!file1.exists()) {
/* 312 */       file1.mkdir();
/*     */     }
/* 314 */     if (!str5.endsWith(str7)) {
/* 315 */       str5 = str5 + str7;
/*     */     }
/* 317 */     str5 = str5 + str9 + str13 + str10;
/* 318 */     this.localLocStrFile = str5;
/* 319 */     File file2 = new File(str5);
/* 320 */     if (file2.exists()) {
/* 321 */       System.out.println(this.localLocStrFile + " already exists.");
/* 322 */       bool = true;
/* 323 */       return bool;
/*     */     } 
/* 325 */     byte[] arrayOfByte = new byte[4096];
/* 326 */     System.out.println("Creating" + this.localLocStrFile + "...");
/*     */ 
/*     */     
/* 329 */     if (null != str12 && true == paramBoolean) {
/*     */       try {
/* 331 */         InputStream inputStream; System.out.println("try localize file from webserver..");
/* 332 */         str1 = this.ParentApp.getCodeBase().getProtocol();
/* 333 */         str2 = this.ParentApp.getCodeBase().getHost();
/* 334 */         i = this.ParentApp.getCodeBase().getPort();
/*     */ 
/*     */         
/* 337 */         if (i >= 0) {
/* 338 */           str3 = ":" + Integer.toString(i);
/*     */         } else {
/*     */           
/* 341 */           str3 = "";
/*     */         } 
/* 343 */         str4 = str1 + "://" + str2 + str3 + "/" + str12;
/* 344 */         System.out.println("trying to retreive webser localize file:" + str4);
/*     */ 
/*     */         
/* 347 */         uRL = new URL(str4);
/*     */ 
/*     */         
/* 350 */         if (str1.equals("http")) {
/* 351 */           httpURLConnection = null;
/* 352 */           httpURLConnection = (HttpURLConnection)uRL.openConnection();
/* 353 */           httpURLConnection.setRequestMethod("GET");
/* 354 */           httpURLConnection.setDoOutput(true);
/* 355 */           httpURLConnection.setUseCaches(false);
/* 356 */           httpURLConnection.connect();
/*     */           
/* 358 */           inputStream = httpURLConnection.getInputStream();
/*     */         } else {
/*     */           
/* 361 */           httpsURLConnection = null;
/* 362 */           httpsURLConnection = (HttpsURLConnection)uRL.openConnection();
/* 363 */           httpsURLConnection.setRequestMethod("GET");
/* 364 */           httpsURLConnection.setDoOutput(true);
/* 365 */           httpsURLConnection.setUseCaches(false);
/* 366 */           httpsURLConnection.connect();
/*     */           
/* 368 */           inputStream = httpsURLConnection.getInputStream();
/*     */         } 
/*     */         
/* 371 */         FileOutputStream fileOutputStream = new FileOutputStream(this.localLocStrFile); int j;
/* 372 */         while ((j = inputStream.read(arrayOfByte, 0, 4096)) != -1)
/* 373 */           fileOutputStream.write(arrayOfByte, 0, j); 
/* 374 */         System.out.println("Write xml to" + this.localLocStrFile + "complete");
/* 375 */         inputStream.close();
/* 376 */         fileOutputStream.close();
/* 377 */         bool = true;
/* 378 */         System.out.println("Message after comp of webserver retrieval");
/*     */       
/*     */       }
/* 381 */       catch (Exception exception) {
/*     */         
/* 383 */         String str = System.getProperty("line.separator");
/* 384 */         this.rcErrMessage = exception.getMessage() + "." + str + str + "Your browser session may have timed out.";
/* 385 */         exception.printStackTrace();
/*     */       }
/*     */       finally {
/*     */         
/* 389 */         if (str1.equals("http")) {
/* 390 */           httpURLConnection.disconnect();
/* 391 */           httpURLConnection = null;
/*     */         } else {
/*     */           
/* 394 */           httpsURLConnection.disconnect();
/* 395 */           httpsURLConnection = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     if (!bool || !paramBoolean) {
/* 404 */       System.out.println("try localize file from applet..");
/* 405 */       ClassLoader classLoader = getClass().getClassLoader();
/* 406 */       String str = str8 + str9 + str10;
/*     */       try {
/* 408 */         InputStream inputStream = classLoader.getResourceAsStream(str);
/* 409 */         FileOutputStream fileOutputStream = new FileOutputStream(this.localLocStrFile);
/*     */         int j;
/* 411 */         while ((j = inputStream.read(arrayOfByte, 0, 4096)) != -1) {
/* 412 */           fileOutputStream.write(arrayOfByte, 0, j);
/*     */         }
/*     */         
/* 415 */         inputStream.close();
/* 416 */         fileOutputStream.close();
/* 417 */         bool = true;
/* 418 */         System.out.println("Message after default xml initialization");
/*     */       } catch (IOException iOException) {
/*     */         
/* 421 */         System.out.println("xmlExtract: " + iOException);
/* 422 */         this.rcErrMessage = iOException.getMessage();
/* 423 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 427 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initLocStringsDefault() {
/* 441 */     boolean bool = false;
/* 442 */     byte b = 0;
/*     */ 
/*     */ 
/*     */     
/* 446 */     try { System.out.println("Message from beginning of initLocStringsDefault" + this.localLocStrFile);
/*     */       
/* 448 */       bool = retrieveLocStrings(false);
/* 449 */       if (false == bool) {
/* 450 */         b = 2;
/*     */       } else {
/*     */         
/* 453 */         this.file = new File(this.localLocStrFile);
/* 454 */         if (null == this.file) {
/* 455 */           b = 3;
/*     */         } else {
/*     */           
/* 458 */           this.dbf = DocumentBuilderFactory.newInstance();
/* 459 */           if (null == this.dbf) {
/* 460 */             b = 4;
/*     */           } else {
/*     */             
/* 463 */             this.db = this.dbf.newDocumentBuilder();
/* 464 */             if (null == this.db) {
/* 465 */               b = 5;
/*     */             } else {
/*     */               
/* 468 */               this.document = this.db.parse(this.file);
/* 469 */               if (null == this.document)
/* 470 */               { b = 6; }
/*     */               else
/*     */               
/* 473 */               { this.document.getDocumentElement().normalize();
/* 474 */                 bool = true;
/* 475 */                 System.out.println("Message after completion of initLocStringsDefault"); } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 480 */     catch (Exception exception) { String str = System.getProperty("line.separator");
/* 481 */       this.rcErrMessage = exception.getMessage() + "." + str + str + "Could not Parse the localization strings.";
/* 482 */       exception.printStackTrace(); }
/*     */     
/* 484 */     if (false == bool) {
/* 485 */       System.out.println("initLocStringsDefault:Error Parsing Xml file:%d" + b);
/*     */     }
/* 487 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initLocStrings() {
/* 500 */     boolean bool = false;
/* 501 */     String str = null;
/* 502 */     byte b = 0;
/*     */ 
/*     */ 
/*     */     
/* 506 */     try { System.out.println("Message from beginning of initLocStrings" + this.localLocStrFile);
/* 507 */       if (null != this.document) {
/* 508 */         b = 1;
/*     */       } else {
/*     */         
/* 511 */         bool = retrieveLocStrings(true);
/* 512 */         if (false == bool) {
/* 513 */           b = 2;
/*     */         } else {
/*     */           
/* 516 */           this.file = new File(this.localLocStrFile);
/* 517 */           bool = false;
/* 518 */           if (null == this.file) {
/* 519 */             b = 3;
/*     */           } else {
/*     */             
/* 522 */             this.dbf = DocumentBuilderFactory.newInstance();
/* 523 */             if (null == this.dbf) {
/* 524 */               b = 4;
/*     */             } else {
/*     */ 
/*     */               
/*     */               try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 538 */                 str = "http://xml.org/sax/features/external-general-entities";
/* 539 */                 this.dbf.setFeature(str, false);
/*     */                 
/* 541 */                 str = "http://xml.org/sax/features/external-parameter-entities";
/* 542 */                 this.dbf.setFeature(str, false);
/*     */ 
/*     */                 
/* 545 */                 str = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/* 546 */                 this.dbf.setFeature(str, false);
/*     */                 
/* 548 */                 this.dbf.setXIncludeAware(false);
/* 549 */                 this.dbf.setExpandEntityReferences(false);
/*     */               } catch (ParserConfigurationException parserConfigurationException) {
/*     */                 
/* 552 */                 System.out.println("ParserConfigurationException was thrown. The feature '" + str + "' is probably not supported by XML parser.");
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 557 */               this.db = this.dbf.newDocumentBuilder();
/* 558 */               if (null == this.db)
/* 559 */               { b = 5; }
/*     */               else
/*     */               
/* 562 */               { this.document = this.db.parse(this.file);
/* 563 */                 if (null == this.document)
/* 564 */                 { b = 6; }
/*     */                 else
/*     */                 
/* 567 */                 { this.document.getDocumentElement().normalize();
/* 568 */                   bool = true;
/* 569 */                   System.out.println("Message after completion of initLocStrings"); }  } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 574 */     catch (Exception exception) { String str1 = System.getProperty("line.separator");
/* 575 */       this.rcErrMessage = exception.getMessage() + "." + str1 + str1 + "Could not Parse the localization strings.";
/* 576 */       exception.printStackTrace(); }
/*     */     
/* 578 */     if (false == bool)
/*     */     {
/*     */       
/* 581 */       bool = initLocStringsDefault();
/*     */     }
/* 583 */     if (false == bool) {
/* 584 */       System.out.println("Error Parsing Xml file:%d" + b);
/*     */     }
/* 586 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocString(int paramInt) {
/* 599 */     boolean bool = false;
/* 600 */     String str1 = "ID_" + Integer.toHexString(paramInt);
/* 601 */     String str2 = "";
/* 602 */     String str3 = "";
/* 603 */     String str4 = "";
/* 604 */     byte b = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 610 */       if (null == this.document) {
/* 611 */         b = 1;
/*     */       } else {
/*     */         
/* 614 */         Element element = this.document.getElementById(str1);
/* 615 */         if (null == element) {
/* 616 */           b = 2;
/*     */         } else {
/*     */           
/* 619 */           NodeList nodeList = element.getChildNodes();
/* 620 */           if (null == nodeList) {
/* 621 */             b = 3;
/*     */           } else {
/*     */             
/* 624 */             str2 = nodeList.item(0).getNodeValue();
/* 625 */             if (null == str2) {
/* 626 */               b = 4;
/*     */             }
/*     */             else {
/*     */               
/* 630 */               bool = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } catch (Exception exception) {
/* 636 */       exception.printStackTrace();
/*     */     } 
/* 638 */     if (false == bool) {
/* 639 */       str2 = "LS_NF";
/* 640 */       System.out.println("LSFNound:" + str1 + "rval:" + b);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 646 */     int i = str2.indexOf('#');
/* 647 */     if (i >= 0) {
/*     */ 
/*     */       
/* 650 */       str3 = str2.substring(0, i);
/*     */       
/* 652 */       int j = str2.indexOf('#', i + 1);
/* 653 */       str4 = str2.substring(i + 1, j);
/* 654 */       str3 = str3 + this.ParentApp.rebrandToken(str4);
/*     */       
/* 656 */       str3 = str3 + str2.substring(j + 1);
/* 657 */       return str3;
/*     */     } 
/*     */     
/* 660 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpLocStrings() {
/*     */     try {
/* 675 */       NodeList nodeList = this.document.getElementsByTagName("javaIRC");
/*     */       
/* 677 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 678 */         Node node = nodeList.item(b);
/* 679 */         if (node.getNodeType() == 1) {
/* 680 */           Element element = (Element)node;
/*     */           
/* 682 */           NodeList nodeList1 = element.getElementsByTagName("menu");
/*     */           
/* 684 */           for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/* 685 */             Element element1 = (Element)nodeList1.item(b1);
/* 686 */             NodeList nodeList5 = element1.getChildNodes();
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 692 */           NodeList nodeList2 = element.getElementsByTagName("dialog");
/*     */           
/* 694 */           for (byte b2 = 0; b2 < nodeList2.getLength(); b2++) {
/* 695 */             Element element1 = (Element)nodeList2.item(b2);
/* 696 */             NodeList nodeList5 = element1.getChildNodes();
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 702 */           NodeList nodeList3 = element.getElementsByTagName("status");
/*     */           
/* 704 */           for (byte b3 = 0; b3 < nodeList3.getLength(); b3++) {
/* 705 */             Element element1 = (Element)nodeList3.item(b3);
/* 706 */             NodeList nodeList5 = element1.getChildNodes();
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 712 */           NodeList nodeList4 = element.getElementsByTagName("tooltip");
/*     */           
/* 714 */           for (byte b4 = 0; b4 < nodeList4.getLength(); b4++) {
/* 715 */             Element element1 = (Element)nodeList4.item(b4);
/* 716 */             NodeList nodeList5 = element1.getChildNodes();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     catch (Exception exception) {
/*     */       
/* 726 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\MARTIN\Downloads\intgapp4_232.jar!\com\hp\ilo2\intgapp\locinfo.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */