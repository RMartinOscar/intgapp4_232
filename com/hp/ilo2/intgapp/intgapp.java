/*      */ package com.hp.ilo2.intgapp;
/*      */ 
/*      */ import com.hp.ilo2.remcons.OkCancelDialog;
/*      */ import com.hp.ilo2.remcons.URLDialog;
/*      */ import com.hp.ilo2.remcons.remcons;
/*      */ import com.hp.ilo2.remcons.telnet;
/*      */ import com.hp.ilo2.virtdevs.MediaAccess;
/*      */ import com.hp.ilo2.virtdevs.VErrorDialog;
/*      */ import com.hp.ilo2.virtdevs.VFileDialog;
/*      */ import com.hp.ilo2.virtdevs.virtdevs;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.util.Arrays;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JApplet;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JScrollPane;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class intgapp
/*      */   extends JApplet
/*      */   implements Runnable, ActionListener, ItemListener
/*      */ {
/*      */   public virtdevs virtdevsObj;
/*      */   public remcons remconsObj;
/*      */   public locinfo locinfoObj;
/*      */   public jsonparser jsonObj;
/*      */   public String optional_features;
/*      */   public String moniker;
/*      */   public boolean moniker_cached = false;
/*      */   public JFrame dispFrame;
/*      */   public JPanel mainPanel;
/*      */   JMenuBar mainMenuBar;
/*      */   JMenu psMenu;
/*      */   JMenu vdMenu;
/*      */   JMenu kbMenu;
/*      */   JMenu kbCAFMenu;
/*      */   JMenu kbAFMenu;
/*      */   JMenu kbLangMenu;
/*      */   JMenu hlpMenu;
/*      */   int vdmenuIndx;
/*      */   int fdMenuItems;
/*      */   int cdMenuItems;
/*      */   private MediaAccess ma;
/*      */   JCheckBoxMenuItem[] vdMenuItems;
/*      */   public JMenuItem vdMenuItemCrImage;
/*      */   JMenuItem momPress;
/*   67 */   public int blade = 0; JMenuItem pressHold; JMenuItem powerCycle; JMenuItem sysReset; JMenuItem ctlAltDel; JMenuItem numLock; JMenuItem capsLock; JMenuItem ctlAltBack; JMenuItem hotKeys; JMenuItem aboutJirc; JMenuItem[] ctlAltFn; JMenuItem[] AltFn; JCheckBoxMenuItem[] localKbdLayout; JPanel dispStatusBar; JMenuItem mdebug1; JMenuItem mdebug2; JMenuItem mdebug3; JScrollPane scroller; public String enc_key; public String rc_port; public String vm_key; public String vm_port; public String server_name; public String ilo_fqdn; public String enclosure;
/*   68 */   public int bay = 0;
/*   69 */   public byte[] enc_key_val = new byte[16];
/*      */   
/*      */   String rcErrMessage;
/*      */   
/*      */   public int dwidth;
/*      */   public int dheight;
/*      */   public boolean exit = false;
/*      */   public boolean fdSelected = false;
/*      */   public boolean cdSelected = false;
/*      */   public boolean in_enclosure = false;
/*   79 */   private int REMCONS_MAX_FN_KEYS = 12;
/*   80 */   private int REMCONS_MAX_KBD_LAYOUT = 17;
/*      */ 
/*      */   
/*      */   public String getLocalString(int paramInt) {
/*   84 */     String str = "";
/*      */     try {
/*   86 */       str = this.locinfoObj.getLocString(paramInt);
/*      */     } catch (Exception exception) {
/*      */       
/*   89 */       System.out.println("remcons:getLocalString" + exception.getMessage());
/*      */     } 
/*   91 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   public intgapp() {
/*   96 */     this.virtdevsObj = new virtdevs(this);
/*   97 */     this.remconsObj = new remcons(this);
/*   98 */     this.locinfoObj = new locinfo(this);
/*   99 */     this.jsonObj = new jsonparser(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void init() {
/*  104 */     boolean bool = true;
/*      */ 
/*      */     
/*  107 */     System.out.println("Started Retrieving parameters from ILO..");
/*  108 */     String str = this.jsonObj.getJSONRequest("rc_info");
/*  109 */     if (str != null) {
/*      */       
/*  111 */       ApplyRcInfoParameters(str);
/*  112 */       System.out.println("Completed Retrieving parameters from ILO");
/*      */     } 
/*  114 */     bool = this.locinfoObj.initLocStrings();
/*      */     
/*  116 */     this.virtdevsObj.init();
/*  117 */     this.remconsObj.init();
/*      */     
/*  119 */     ui_init();
/*      */     
/*  121 */     if (null == str) {
/*  122 */       System.out.println("Failed to retrive parameters from ILO");
/*  123 */       new VErrorDialog(this.dispFrame, getLocalString(8212), this.rcErrMessage, true);
/*  124 */       this.dispFrame.setVisible(false);
/*      */     }
/*  126 */     else if (false == bool) {
/*  127 */       new VErrorDialog(this.dispFrame, getLocalString(8212), this.locinfoObj.rcErrMessage, true);
/*  128 */       this.dispFrame.setVisible(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void start() {
/*      */     try {
/*  136 */       this.virtdevsObj.start();
/*  137 */       this.remconsObj.start();
/*      */       
/*  139 */       this.dispFrame.getContentPane().add(this.scroller, "Center");
/*  140 */       this.dispFrame.getContentPane().add(this.dispStatusBar, "South");
/*  141 */       this.scroller.validate();
/*  142 */       this.dispStatusBar.validate();
/*  143 */       this.dispFrame.validate();
/*      */ 
/*      */       
/*  146 */       System.out.println("Set Inital focus for session..");
/*  147 */       this.remconsObj.session.requestFocus();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  166 */       run();
/*      */     } catch (Exception exception) {
/*      */       
/*  169 */       System.out.println("FAILURE: exception starting applet");
/*  170 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void stop() {
/*  177 */     this.exit = true;
/*  178 */     this.virtdevsObj.stop();
/*  179 */     this.remconsObj.remconsUnInstallKeyboardHook();
/*  180 */     this.remconsObj.stop();
/*      */   }
/*      */ 
/*      */   
/*      */   public void destroy() {
/*  185 */     System.out.println("Destroying subsustems");
/*  186 */     this.exit = true;
/*  187 */     this.remconsObj.remconsUnInstallKeyboardHook();
/*  188 */     this.virtdevsObj.destroy();
/*  189 */     this.remconsObj.destroy();
/*  190 */     this.dispFrame.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void run() {
/*  201 */     byte b = 0;
/*  202 */     boolean bool = false;
/*      */     while (true) {
/*      */       try {
/*  205 */         b = 0;
/*  206 */         byte b1 = 0;
/*  207 */         this.ma = new MediaAccess();
/*  208 */         String[] arrayOfString = this.ma.devices();
/*  209 */         for (byte b2 = 0; arrayOfString != null && b2 < arrayOfString.length; b2++) {
/*  210 */           int i = this.ma.devtype(arrayOfString[b2]);
/*  211 */           if (i == 2 || i == 5) {
/*  212 */             b1++;
/*      */           }
/*      */         } 
/*      */         
/*  216 */         if (b1 > this.vdmenuIndx - 4) {
/*  217 */           ClassLoader classLoader = getClass().getClassLoader();
/*  218 */           for (byte b3 = 0; arrayOfString != null && b3 < arrayOfString.length; b3++) {
/*  219 */             bool = false;
/*  220 */             int i = this.ma.devtype(arrayOfString[b3]);
/*  221 */             for (byte b4 = 0; b4 < this.vdmenuIndx - 4; b4++) {
/*  222 */               if (arrayOfString[b3].equals(this.vdMenu.getItem(b4).getText())) {
/*  223 */                 bool = true;
/*  224 */                 b++;
/*      */               } 
/*      */             } 
/*  227 */             if (!bool) {
/*  228 */               if (i == 2) {
/*  229 */                 System.out.println("Device attached: " + arrayOfString[b3]);
/*  230 */                 this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(arrayOfString[b3]);
/*  231 */                 this.vdMenuItems[this.vdmenuIndx].setActionCommand("fd" + arrayOfString[b3]);
/*  232 */                 this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  233 */                 if (arrayOfString[b3].equals("A:") || arrayOfString[b3].equals("B:")) {
/*  234 */                   this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/FloppyDisk.png"))));
/*      */                 } else {
/*  236 */                   this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/usb.png"))));
/*      */                 } 
/*  238 */                 this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx], b);
/*  239 */                 this.vdMenu.updateUI();
/*  240 */                 this.vdmenuIndx++;
/*      */                 break;
/*      */               } 
/*  243 */               if (i == 5) {
/*  244 */                 System.out.println("CDROM Hot plug device auto-update no supported at this time");
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/*  249 */         } else if (b1 < this.vdmenuIndx - 4) {
/*  250 */           for (byte b3 = 0; b3 < this.vdmenuIndx - 4; b3++) {
/*  251 */             bool = false;
/*  252 */             for (byte b4 = 0; arrayOfString != null && b4 < arrayOfString.length; b4++) {
/*  253 */               int i = this.ma.devtype(arrayOfString[b4]);
/*  254 */               if ((i == 2 || i == 5) && 
/*  255 */                 this.vdMenu.getItem(b3).getText().equals(arrayOfString[b4])) {
/*  256 */                 bool = true;
/*      */               }
/*      */             } 
/*      */             
/*  260 */             if (!bool) {
/*  261 */               System.out.println("Device removed: " + this.vdMenu.getItem(b3).getText());
/*  262 */               this.vdMenu.remove(b3);
/*  263 */               this.vdMenu.updateUI();
/*  264 */               this.vdmenuIndx--;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  269 */         this.ma = null;
/*  270 */         this.remconsObj.session.set_status(3, "");
/*  271 */         this.remconsObj.sleepAtLeast(5000L);
/*  272 */         if (this.exit) {
/*      */           break;
/*      */         }
/*      */       } catch (InterruptedException interruptedException) {
/*      */         
/*  277 */         System.out.println("Exception on intgapp");
/*      */       } 
/*      */     } 
/*  280 */     System.out.println("Intgapp stopped running");
/*      */   }
/*      */ 
/*      */   
/*      */   public void paintComponent(Graphics paramGraphics) {
/*  285 */     paintComponents(paramGraphics);
/*  286 */     paramGraphics.drawString("Remote Console JApplet Loaded", 10, 50);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ui_init() {
/*  291 */     String str1 = "";
/*      */     
/*  293 */     System.out.println("Message from ui_init55");
/*  294 */     this.dispFrame = new JFrame("JavaApplet IRC Window");
/*  295 */     this.dispFrame.getContentPane().setLayout(new BorderLayout());
/*  296 */     this.dispFrame.addWindowListener(new WindowCloser(this));
/*  297 */     this.mainMenuBar = new JMenuBar();
/*  298 */     this.dispStatusBar = new JPanel(new BorderLayout());
/*  299 */     this.dispStatusBar.add(((telnet)this.remconsObj.session).status_box, "West");
/*  300 */     this.dispStatusBar.add(this.remconsObj.pwrStatusPanel, "East");
/*      */ 
/*      */     
/*  303 */     String str3 = this.jsonObj.getJSONRequest("session_info");
/*  304 */     JPopupMenu.setDefaultLightWeightPopupEnabled(false);
/*  305 */     this.dispFrame.setJMenuBar(this.mainMenuBar);
/*  306 */     if (str3 != null) {
/*      */       
/*  308 */       makePsMenu(this.mainMenuBar, this.jsonObj.getJSONNumber(str3, "reset_priv"));
/*  309 */       makeVdMenu(this.mainMenuBar, this.jsonObj.getJSONNumber(str3, "virtual_media_priv"));
/*      */     } 
/*  311 */     makeKbMenu(this.mainMenuBar);
/*  312 */     String str2 = this.jsonObj.getJSONRequest("login_session");
/*      */     
/*  314 */     if (str2 != null) {
/*      */       
/*  316 */       str1 = this.jsonObj.getJSONObject(str2, "alt");
/*  317 */       if (str1 == null || (str1 != null && this.jsonObj.getJSONNumber(str1, "mode") == 0))
/*      */       {
/*  319 */         makeHlpMenu(this.mainMenuBar);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  324 */     this.scroller = new JScrollPane((Component)this.remconsObj.session, 20, 30);
/*  325 */     this.scroller.setVisible(true);
/*      */ 
/*      */     
/*      */     try {
/*  329 */       String str = getLocalString(4132) + " " + this.server_name + " " + getLocalString(4133) + " " + this.ilo_fqdn;
/*      */       
/*  331 */       if (this.blade == 1 && this.in_enclosure == true) {
/*  332 */         str = str + " " + getLocalString(4134) + " " + this.enclosure + " " + getLocalString(4135) + " " + this.bay;
/*      */       }
/*      */       
/*  335 */       this.dispFrame.setTitle(str);
/*      */     } catch (Exception exception) {
/*      */       
/*  338 */       this.dispFrame.setTitle(getLocalString(4132) + " " + getCodeBase().getHost());
/*  339 */       System.out.println("IRC title not available");
/*      */     } 
/*  341 */     int i = (Toolkit.getDefaultToolkit().getScreenSize()).width;
/*  342 */     int j = (Toolkit.getDefaultToolkit().getScreenSize()).height;
/*      */     
/*  344 */     boolean bool1 = (i < 1054) ? i : true;
/*  345 */     boolean bool2 = (j < 874) ? (j - 30) : true;
/*  346 */     boolean bool3 = (i > 1054) ? ((i - 1054) / 2) : false;
/*  347 */     boolean bool4 = (j > 874) ? ((j - 874) / 2) : false;
/*      */     
/*  349 */     this.dispFrame.setSize(bool1, bool2);
/*  350 */     this.dispFrame.setLocation(bool3, bool4);
/*  351 */     System.out.println("check dimensions " + bool1 + " " + bool2 + " " + bool3 + " " + bool4);
/*  352 */     this.dispFrame.setVisible(true);
/*      */     
/*      */     try {
/*  355 */       Insets insets = this.dispFrame.getInsets();
/*  356 */       ClassLoader classLoader = getClass().getClassLoader();
/*  357 */       if (str1 == null || (str1 != null && this.jsonObj.getJSONNumber(str1, "mode") == 0))
/*      */       {
/*  359 */         this.dispFrame.setIconImage(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/ilo_logo.png")));
/*      */       }
/*  361 */       Image image = this.dispFrame.getIconImage();
/*  362 */       if (image == null) {
/*  363 */         System.out.println("Dimage is null");
/*      */       }
/*      */     } catch (Exception exception) {
/*      */       
/*  367 */       System.out.println("JIRC icon not available");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void makeHlpMenu(JMenuBar paramJMenuBar) {
/*  377 */     this.hlpMenu = new JMenu(getLocalString(4136));
/*  378 */     this.aboutJirc = new JMenuItem(getLocalString(4137));
/*  379 */     this.aboutJirc.addActionListener(this);
/*  380 */     this.hlpMenu.add(this.aboutJirc);
/*  381 */     paramJMenuBar.add(this.hlpMenu);
/*      */   }
/*      */   
/*      */   protected void makeVdMenu(JMenuBar paramJMenuBar, int paramInt) {
/*  385 */     this.vdMenu = new JMenu(getLocalString(4098));
/*  386 */     if (paramInt == 1) {
/*  387 */       paramJMenuBar.add(this.vdMenu);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateVdMenu() {
/*  394 */     this.ma = new MediaAccess();
/*  395 */     ClassLoader classLoader = getClass().getClassLoader();
/*      */     
/*  397 */     String str1 = this.jsonObj.getJSONRequest("vm_status");
/*      */     
/*  399 */     String str2 = this.jsonObj.getJSONArray(str1, "options", 0);
/*      */     
/*  401 */     String str3 = this.jsonObj.getJSONArray(str1, "options", 1);
/*      */ 
/*      */     
/*  404 */     String[] arrayOfString = this.ma.devices();
/*  405 */     this.vdmenuIndx = 0;
/*  406 */     if (arrayOfString != null) {
/*  407 */       this.vdMenuItems = new JCheckBoxMenuItem[arrayOfString.length + 5];
/*  408 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  409 */         int i = this.ma.devtype(arrayOfString[b]);
/*  410 */         if (i == 5) {
/*  411 */           this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(arrayOfString[b]);
/*  412 */           this.vdMenuItems[this.vdmenuIndx].setActionCommand("cd" + arrayOfString[b]);
/*  413 */           this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  414 */           this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/CD_Drive.png"))));
/*  415 */           this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx]);
/*  416 */           this.vdmenuIndx++;
/*      */         }
/*  418 */         else if (i == 2) {
/*  419 */           this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(arrayOfString[b]);
/*  420 */           this.vdMenuItems[this.vdmenuIndx].setActionCommand("fd" + arrayOfString[b]);
/*  421 */           this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  422 */           if (arrayOfString[b].equals("A:") || arrayOfString[b].equals("B:")) {
/*  423 */             this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/FloppyDisk.png"))));
/*      */           } else {
/*  425 */             this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/usb.png"))));
/*      */           } 
/*  427 */           this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx]);
/*  428 */           this.vdmenuIndx++;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  432 */       this.vdMenuItems = new JCheckBoxMenuItem[5];
/*  433 */       System.out.println("Media Access not available...");
/*      */     } 
/*  435 */     this.ma = null;
/*      */ 
/*      */     
/*  438 */     this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(getLocalString(4130) + " " + getLocalString(4106));
/*  439 */     this.vdMenuItems[this.vdmenuIndx].setActionCommand("fd" + getLocalString(12567));
/*  440 */     this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  441 */     this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Image_File.png"))));
/*  442 */     this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx]);
/*  443 */     this.vdmenuIndx++;
/*      */     
/*  445 */     this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(getLocalString(4131) + getLocalString(4106));
/*  446 */     this.vdMenuItems[this.vdmenuIndx].setActionCommand("FLOPPY");
/*  447 */     this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Network.png"))));
/*  448 */     this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx]);
/*  449 */     this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  450 */     this.vdmenuIndx++;
/*  451 */     if (this.jsonObj.getJSONNumber(str2, "vm_url_connected") == 1 && this.jsonObj.getJSONNumber(str2, "vm_connected") == 1) {
/*  452 */       this.fdSelected = true;
/*  453 */       lockFdMenu(false, "URL Removable Media");
/*      */     } 
/*      */ 
/*      */     
/*  457 */     this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(getLocalString(4130) + " " + getLocalString(4107));
/*  458 */     this.vdMenuItems[this.vdmenuIndx].setActionCommand("cd" + getLocalString(12567));
/*  459 */     this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  460 */     this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Image_File.png"))));
/*  461 */     this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx]);
/*  462 */     this.vdmenuIndx++;
/*      */     
/*  464 */     this.vdMenuItems[this.vdmenuIndx] = new JCheckBoxMenuItem(getLocalString(4131) + getLocalString(4107));
/*  465 */     this.vdMenuItems[this.vdmenuIndx].setActionCommand("CDROM");
/*  466 */     this.vdMenuItems[this.vdmenuIndx].setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Network.png"))));
/*  467 */     this.vdMenu.add(this.vdMenuItems[this.vdmenuIndx]);
/*  468 */     this.vdMenuItems[this.vdmenuIndx].addItemListener(this);
/*  469 */     this.vdmenuIndx++;
/*  470 */     if (this.jsonObj.getJSONNumber(str3, "vm_url_connected") == 1 && this.jsonObj.getJSONNumber(str3, "vm_connected") == 1) {
/*  471 */       this.cdSelected = true;
/*  472 */       lockCdMenu(false, "URL CD/DVD-ROM");
/*      */     } 
/*      */ 
/*      */     
/*  476 */     this.vdMenu.addSeparator();
/*  477 */     this.vdMenuItemCrImage = new JMenuItem(getLocalString(4109));
/*  478 */     this.vdMenuItemCrImage.setActionCommand("CreateDiskImage");
/*  479 */     this.vdMenuItemCrImage.addActionListener(this);
/*  480 */     this.vdMenu.add(this.vdMenuItemCrImage);
/*      */   }
/*      */ 
/*      */   
/*      */   public void lockCdMenu(boolean paramBoolean, String paramString) {
/*  485 */     byte b = 0;
/*      */     
/*  487 */     for (b = 0; b < this.vdmenuIndx; b++) {
/*  488 */       this.vdMenu.getItem(b).removeItemListener(this);
/*      */ 
/*      */       
/*  491 */       if (this.vdMenu.getItem(b).getActionCommand().startsWith("cd") || this.vdMenu.getItem(b).getActionCommand().equals("CDROM"))
/*      */       {
/*  493 */         if (paramString.equals(this.vdMenu.getItem(b).getText())) {
/*      */ 
/*      */           
/*  496 */           this.vdMenu.getItem(b).setSelected(!paramBoolean);
/*      */         } else {
/*      */           
/*  499 */           this.vdMenu.getItem(b).setSelected(false);
/*  500 */           this.vdMenu.getItem(b).setEnabled(paramBoolean);
/*      */         } 
/*      */       }
/*  503 */       this.vdMenu.getItem(b).addItemListener(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void lockFdMenu(boolean paramBoolean, String paramString) {
/*  509 */     byte b = 0;
/*      */     
/*  511 */     for (b = 0; b < this.vdmenuIndx; b++) {
/*  512 */       this.vdMenu.getItem(b).removeItemListener(this);
/*      */       
/*  514 */       if (this.vdMenu.getItem(b).getActionCommand().startsWith("fd") || this.vdMenu.getItem(b).getActionCommand().equals("FLOPPY"))
/*      */       {
/*  516 */         if (paramString.equals(this.vdMenu.getItem(b).getText())) {
/*      */ 
/*      */           
/*  519 */           this.vdMenu.getItem(b).setSelected(!paramBoolean);
/*      */         } else {
/*      */           
/*  522 */           this.vdMenu.getItem(b).setSelected(false);
/*  523 */           this.vdMenu.getItem(b).setEnabled(paramBoolean);
/*      */         } 
/*      */       }
/*  526 */       this.vdMenu.getItem(b).addItemListener(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void makePsMenu(JMenuBar paramJMenuBar, int paramInt) {
/*  532 */     ClassLoader classLoader = getClass().getClassLoader();
/*      */     
/*  534 */     this.psMenu = new JMenu(getLocalString(4097));
/*      */     
/*  536 */     this.momPress = new JMenuItem(getLocalString(4100));
/*  537 */     this.momPress.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/press.png"))));
/*  538 */     this.momPress.setActionCommand("psMomPress");
/*  539 */     this.momPress.addActionListener(this);
/*  540 */     this.psMenu.add(this.momPress);
/*      */     
/*  542 */     this.pressHold = new JMenuItem(getLocalString(4101));
/*  543 */     this.pressHold.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/hold.png"))));
/*  544 */     this.pressHold.setActionCommand("psPressHold");
/*  545 */     this.pressHold.addActionListener(this);
/*      */     
/*  547 */     this.powerCycle = new JMenuItem(getLocalString(4102));
/*  548 */     this.powerCycle.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/coldboot.png"))));
/*  549 */     this.powerCycle.setActionCommand("psPowerCycle");
/*  550 */     this.powerCycle.addActionListener(this);
/*      */     
/*  552 */     this.sysReset = new JMenuItem(getLocalString(4103));
/*  553 */     this.sysReset.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/reset.png"))));
/*  554 */     this.sysReset.setActionCommand("psSysReset");
/*  555 */     this.sysReset.addActionListener(this);
/*      */     
/*  557 */     if (paramInt == 1) {
/*  558 */       paramJMenuBar.add(this.psMenu);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePsMenu(int paramInt) {
/*  564 */     if (0 == paramInt) {
/*  565 */       this.psMenu.remove(this.pressHold);
/*  566 */       this.psMenu.remove(this.powerCycle);
/*  567 */       this.psMenu.remove(this.sysReset);
/*      */     }
/*      */     else {
/*      */       
/*  571 */       this.psMenu.remove(this.pressHold);
/*  572 */       this.psMenu.remove(this.powerCycle);
/*  573 */       this.psMenu.remove(this.sysReset);
/*      */       
/*  575 */       this.psMenu.add(this.pressHold);
/*  576 */       this.psMenu.add(this.powerCycle);
/*  577 */       this.psMenu.add(this.sysReset);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void makeKbMenu(JMenuBar paramJMenuBar) {
/*  584 */     ClassLoader classLoader = getClass().getClassLoader();
/*  585 */     this.kbMenu = new JMenu(getLocalString(4099));
/*  586 */     this.kbCAFMenu = new JMenu("CTRL-ALT-Fn");
/*  587 */     this.kbAFMenu = new JMenu("ALT-Fn");
/*  588 */     this.kbLangMenu = new JMenu(getLocalString(4110));
/*      */     
/*  590 */     this.ctlAltDel = new JMenuItem(getLocalString(4104));
/*  591 */     this.ctlAltDel.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Keyboard.png"))));
/*  592 */     this.ctlAltDel.setActionCommand("kbCtlAltDel");
/*  593 */     this.ctlAltDel.addActionListener(this);
/*  594 */     this.kbMenu.add(this.ctlAltDel);
/*      */     
/*  596 */     this.numLock = new JMenuItem(getLocalString(4105));
/*  597 */     this.numLock.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Keyboard.png"))));
/*  598 */     this.numLock.setActionCommand("kbNumLock");
/*  599 */     this.numLock.addActionListener(this);
/*  600 */     this.kbMenu.add(this.numLock);
/*      */     
/*  602 */     this.capsLock = new JMenuItem(getLocalString(4128));
/*  603 */     this.capsLock.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Keyboard.png"))));
/*  604 */     this.capsLock.setActionCommand("kbCapsLock");
/*  605 */     this.capsLock.addActionListener(this);
/*  606 */     this.kbMenu.add(this.capsLock);
/*      */     
/*  608 */     this.ctlAltBack = new JMenuItem("CTRL-ALT-BACKSPACE");
/*  609 */     this.ctlAltBack.setIcon(new ImageIcon(getImage(classLoader.getResource("com/hp/ilo2/remcons/images/Keyboard.png"))));
/*  610 */     this.ctlAltBack.setActionCommand("kbCtlAltBack");
/*  611 */     this.ctlAltBack.addActionListener(this);
/*      */ 
/*      */     
/*  614 */     this.ctlAltFn = new JMenuItem[this.REMCONS_MAX_FN_KEYS]; byte b;
/*  615 */     for (b = 0; b < this.REMCONS_MAX_FN_KEYS; b++) {
/*  616 */       this.ctlAltFn[b] = new JMenuItem("CTRL-ALT-F" + (b + 1));
/*  617 */       this.ctlAltFn[b].setActionCommand("kbCtrlAltFn" + b);
/*      */ 
/*      */       
/*  620 */       this.ctlAltFn[b].addActionListener(this);
/*  621 */       this.kbCAFMenu.add(this.ctlAltFn[b]);
/*      */     } 
/*  623 */     this.AltFn = new JMenuItem[this.REMCONS_MAX_FN_KEYS];
/*  624 */     for (b = 0; b < this.REMCONS_MAX_FN_KEYS; b++) {
/*  625 */       this.AltFn[b] = new JMenuItem("ALT-F" + (b + 1));
/*  626 */       this.AltFn[b].setActionCommand("kbAltFn" + b);
/*  627 */       this.AltFn[b].addActionListener(this);
/*  628 */       this.kbAFMenu.add(this.AltFn[b]);
/*      */     } 
/*      */     
/*  631 */     this.localKbdLayout = new JCheckBoxMenuItem[this.REMCONS_MAX_KBD_LAYOUT];
/*  632 */     for (b = 0; b < this.REMCONS_MAX_KBD_LAYOUT; b++) {
/*  633 */       this.localKbdLayout[b] = new JCheckBoxMenuItem(getLocalString(4111 + b));
/*  634 */       this.localKbdLayout[b].setActionCommand("localKbdLayout" + b);
/*  635 */       this.localKbdLayout[b].addItemListener(this);
/*  636 */       this.kbLangMenu.add(this.localKbdLayout[b]);
/*      */     } 
/*  638 */     this.localKbdLayout[0].setSelected(true);
/*      */     
/*  640 */     String str = System.getProperty("os.name").toLowerCase();
/*  641 */     if (!str.startsWith("windows")) {
/*  642 */       this.kbMenu.add(this.ctlAltBack);
/*  643 */       this.kbMenu.add(this.kbCAFMenu);
/*  644 */       this.kbMenu.add(this.kbAFMenu);
/*  645 */       this.kbMenu.add(this.kbLangMenu);
/*      */     } 
/*      */     
/*  648 */     this.kbMenu.addSeparator();
/*  649 */     this.hotKeys = new JMenuItem(getLocalString(4129));
/*  650 */     this.hotKeys.addActionListener(this);
/*  651 */     this.kbMenu.add(this.hotKeys);
/*      */     
/*  653 */     paramJMenuBar.add(this.kbMenu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPerformed(ActionEvent paramActionEvent) {
/*  662 */     if (paramActionEvent.getSource() == this.momPress) {
/*  663 */       OkCancelDialog okCancelDialog = new OkCancelDialog(this.remconsObj, this.dispFrame, getLocalString(8297), getLocalString(4097));
/*  664 */       if (okCancelDialog.result() == true) {
/*  665 */         this.remconsObj.session.sendMomPress();
/*      */       }
/*  667 */     } else if (paramActionEvent.getSource() == this.pressHold) {
/*  668 */       OkCancelDialog okCancelDialog = new OkCancelDialog(this.remconsObj, this.dispFrame, getLocalString(8298), getLocalString(4097));
/*  669 */       if (okCancelDialog.result() == true) {
/*  670 */         this.remconsObj.session.sendPressHold();
/*      */       }
/*  672 */     } else if (paramActionEvent.getSource() == this.powerCycle) {
/*  673 */       OkCancelDialog okCancelDialog = new OkCancelDialog(this.remconsObj, this.dispFrame, getLocalString(8299), getLocalString(4097));
/*  674 */       if (okCancelDialog.result() == true) {
/*  675 */         this.remconsObj.session.sendPowerCycle();
/*      */       }
/*  677 */     } else if (paramActionEvent.getSource() == this.sysReset) {
/*  678 */       OkCancelDialog okCancelDialog = new OkCancelDialog(this.remconsObj, this.dispFrame, getLocalString(8300), getLocalString(4097));
/*  679 */       if (okCancelDialog.result() == true) {
/*  680 */         this.remconsObj.session.sendSystemReset();
/*      */       
/*      */       }
/*      */     }
/*  684 */     else if (paramActionEvent.getSource() == this.ctlAltDel) {
/*  685 */       this.remconsObj.session.send_ctrl_alt_del();
/*      */     }
/*  687 */     else if (paramActionEvent.getSource() == this.numLock) {
/*  688 */       this.remconsObj.session.send_num_lock();
/*      */     }
/*  690 */     else if (paramActionEvent.getSource() == this.capsLock) {
/*  691 */       this.remconsObj.session.send_caps_lock();
/*      */     }
/*  693 */     else if (paramActionEvent.getSource() == this.ctlAltBack) {
/*  694 */       this.remconsObj.session.send_ctrl_alt_back();
/*      */     }
/*  696 */     else if (paramActionEvent.getSource() == this.hotKeys) {
/*  697 */       this.remconsObj.viewHotKeys();
/*      */ 
/*      */     
/*      */     }
/*  701 */     else if (paramActionEvent.getSource() == this.vdMenuItemCrImage) {
/*  702 */       this.virtdevsObj.createImage();
/*      */     
/*      */     }
/*  705 */     else if (paramActionEvent.getSource() == this.aboutJirc) {
/*  706 */       this.remconsObj.viewAboutJirc();
/*      */     }
/*      */     else {
/*      */       
/*  710 */       for (byte b = 0; b < this.REMCONS_MAX_FN_KEYS; b++) {
/*  711 */         if (paramActionEvent.getSource() == this.ctlAltFn[b]) {
/*  712 */           this.remconsObj.session.send_ctrl_alt_fn(b);
/*      */           break;
/*      */         } 
/*  715 */         if (paramActionEvent.getSource() == this.AltFn[b]) {
/*  716 */           this.remconsObj.session.send_alt_fn(b);
/*      */           break;
/*      */         } 
/*      */       } 
/*  720 */       if (b >= this.REMCONS_MAX_FN_KEYS) {
/*  721 */         System.out.println("Unhandled ActionItem" + paramActionEvent.getActionCommand());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void itemStateChanged(ItemEvent paramItemEvent) {
/*  729 */     boolean bool = false;
/*  730 */     JCheckBoxMenuItem jCheckBoxMenuItem = null;
/*  731 */     String str1 = null;
/*  732 */     String str2 = null;
/*  733 */     int i = paramItemEvent.getStateChange();
/*      */     
/*      */     byte b;
/*  736 */     for (b = 0; b < this.REMCONS_MAX_KBD_LAYOUT; b++) {
/*  737 */       if (this.localKbdLayout[b] == paramItemEvent.getSource() && i == 1) {
/*  738 */         System.out.println(b);
/*  739 */         this.localKbdLayout[b].setSelected(true);
/*  740 */         kbdLayoutMenuHandler(b);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  746 */     for (b = 0; b < this.vdmenuIndx; b++) {
/*  747 */       if (this.vdMenuItems[b] == paramItemEvent.getSource()) {
/*  748 */         jCheckBoxMenuItem = this.vdMenuItems[b];
/*  749 */         str1 = jCheckBoxMenuItem.getActionCommand();
/*      */         
/*  751 */         str2 = jCheckBoxMenuItem.getLabel();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  757 */     if (jCheckBoxMenuItem == null || str1 == null) {
/*  758 */       System.out.println("Unhandled item event");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  763 */     if (str1.equals("fd" + getLocalString(12567))) {
/*  764 */       String str = null;
/*  765 */       if (i == 2) {
/*  766 */         bool = this.virtdevsObj.do_floppy(str2);
/*  767 */         lockFdMenu(true, str2);
/*      */       }
/*  769 */       else if (i == 1) {
/*      */         
/*  771 */         this.dispFrame.setVisible(false);
/*  772 */         VFileDialog vFileDialog = new VFileDialog(getLocalString(8261), "*.img");
/*  773 */         str = vFileDialog.getString();
/*  774 */         this.dispFrame.setVisible(true);
/*      */         
/*  776 */         if (str != null) {
/*  777 */           if (this.virtdevsObj.fdThread != null)
/*  778 */             this.virtdevsObj.change_disk(this.virtdevsObj.fdConnection, str); 
/*  779 */           System.out.println("Image file: " + str);
/*  780 */           bool = this.virtdevsObj.do_floppy(str);
/*  781 */           if (!bool) {
/*      */             
/*  783 */             lockFdMenu(true, str2);
/*      */           } else {
/*  785 */             lockFdMenu(false, str2);
/*      */           } 
/*      */         } else {
/*  788 */           lockFdMenu(true, str2);
/*      */         } 
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  795 */     if (str1.equals("cd" + getLocalString(12567))) {
/*  796 */       String str = null;
/*  797 */       if (i == 2) {
/*  798 */         bool = this.virtdevsObj.do_cdrom(str2);
/*  799 */         lockCdMenu(true, str2);
/*      */       }
/*  801 */       else if (i == 1) {
/*      */         
/*  803 */         this.dispFrame.setVisible(false);
/*  804 */         VFileDialog vFileDialog = new VFileDialog(getLocalString(8261), "*.iso");
/*  805 */         str = vFileDialog.getString();
/*  806 */         this.dispFrame.setVisible(true);
/*      */         
/*  808 */         if (str != null) {
/*  809 */           if (this.virtdevsObj.cdThread != null)
/*  810 */             this.virtdevsObj.change_disk(this.virtdevsObj.cdConnection, str); 
/*  811 */           System.out.println("Image file: " + str);
/*  812 */           bool = this.virtdevsObj.do_cdrom(str);
/*  813 */           if (!bool) {
/*      */             
/*  815 */             lockCdMenu(true, str2);
/*      */           } else {
/*  817 */             lockCdMenu(false, str2);
/*      */           } 
/*      */         } else {
/*  820 */           lockCdMenu(true, str2);
/*      */         } 
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  827 */     if (str1.startsWith("cd")) {
/*  828 */       bool = this.virtdevsObj.do_cdrom(str2);
/*  829 */       if (bool)
/*      */       {
/*  831 */         lockCdMenu((i != 1), str2);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  837 */     if (str1.startsWith("fd")) {
/*  838 */       bool = this.virtdevsObj.do_floppy(str2);
/*  839 */       if (bool)
/*      */       {
/*  841 */         lockFdMenu((i != 1), str2);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  847 */     if (str1.equals("FLOPPY") || str1.equals("CDROM")) {
/*      */ 
/*      */       
/*  850 */       String str = "";
/*  851 */       boolean bool1 = false;
/*      */       
/*  853 */       if (i == 2) {
/*  854 */         String str3 = "{\"method\":\"set_virtual_media_options\", \"device\":\"" + str1 + "\", \"command\":\"EJECT\", \"session_key\":\"" + getParameter("RCINFO1") + "\"}";
/*  855 */         str = this.jsonObj.postJSONRequest("vm_status", str3);
/*  856 */         this.remconsObj.session.set_status(3, "Unmounted URL");
/*      */       }
/*  858 */       else if (i == 1) {
/*  859 */         this.remconsObj.setDialogIsOpen(true);
/*  860 */         URLDialog uRLDialog = new URLDialog(this.remconsObj);
/*  861 */         String str3 = uRLDialog.getUserInput();
/*      */         
/*  863 */         if (str3.compareTo("userhitcancel") == 0 || str3.compareTo("userhitclose") == 0) {
/*  864 */           str3 = null;
/*      */         }
/*      */         
/*  867 */         if (str3 != null) {
/*  868 */           str3 = str3.replaceAll("[\000-\037]", "");
/*  869 */           System.out.println("url:  " + str3);
/*      */         } 
/*      */         
/*  872 */         this.remconsObj.setDialogIsOpen(false);
/*  873 */         if (str3 != null) {
/*  874 */           String str4 = "{\"method\":\"set_virtual_media_options\", \"device\":\"" + str1 + "\", \"command\":\"INSERT\", \"url\":\"" + str3 + "\", \"session_key\":\"" + getParameter("RCINFO1") + "\"}";
/*      */           
/*  876 */           str = this.jsonObj.postJSONRequest("vm_status", str4);
/*  877 */           if (str == "Success") {
/*  878 */             str4 = "{\"method\":\"set_virtual_media_options\", \"device\":\"" + str1 + "\", \"boot_option\":\"CONNECT\", \"command\":\"SET\", \"url\":\"" + str3 + "\", \"session_key\":\"" + getParameter("RCINFO1") + "\"}";
/*      */             
/*  880 */             str = this.jsonObj.postJSONRequest("vm_status", str4);
/*      */           } 
/*      */           
/*  883 */           if (str == "SCSI_ERR_NO_LICENSE") {
/*  884 */             String str5 = "<html>" + getLocalString(8213) + " " + getLocalString(8214) + " " + getLocalString(8237) + "<br><br>" + getLocalString(8238) + "</html>";
/*      */             
/*  886 */             new VErrorDialog(this.dispFrame, getLocalString(8236), str5, true);
/*      */           }
/*  888 */           else if (str != "Success") {
/*  889 */             new VErrorDialog(this.dispFrame, getLocalString(8212), getLocalString(8292), true);
/*      */           } else {
/*  891 */             bool1 = true;
/*  892 */             this.remconsObj.session.set_status(3, getLocalString(12581));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  897 */       if (str1.equals("FLOPPY")) {
/*  898 */         lockFdMenu(!bool1, str2);
/*      */       }
/*  900 */       else if (str1.equals("CDROM")) {
/*      */         
/*  902 */         lockCdMenu(!bool1, str2);
/*      */       } 
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void kbdLayoutMenuHandler(int paramInt) {
/*  914 */     for (byte b = 0; b < this.REMCONS_MAX_KBD_LAYOUT; b++) {
/*  915 */       if (b != paramInt) {
/*  916 */         this.localKbdLayout[b].setSelected(false);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  921 */     this.remconsObj.setLocalKbdLayout(paramInt);
/*      */   } class WindowCloser extends WindowAdapter { private final intgapp this$0;
/*      */     WindowCloser(intgapp this$0) {
/*  924 */       this.this$0 = this$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void windowClosing(WindowEvent param1WindowEvent) {
/*  929 */       this.this$0.stop();
/*  930 */       this.this$0.exit = true;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ApplyRcInfoParameters(String paramString) {
/*  937 */     this.enc_key = this.rc_port = this.vm_key = this.vm_port = null;
/*  938 */     Arrays.fill(this.enc_key_val, (byte)0);
/*      */ 
/*      */     
/*  941 */     paramString = paramString.trim();
/*  942 */     paramString = paramString.substring(1, paramString.length() - 1);
/*  943 */     String[] arrayOfString = paramString.split(",");
/*      */     
/*  945 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  946 */       String[] arrayOfString1 = arrayOfString[b].split(":");
/*  947 */       if (arrayOfString1.length != 2) {
/*  948 */         System.out.println("Error in ApplyRcInfoParameters");
/*      */         
/*      */         return;
/*      */       } 
/*  952 */       String str1 = arrayOfString1[0].trim();
/*  953 */       str1 = str1.substring(1, str1.length() - 1);
/*      */       
/*  955 */       String str2 = arrayOfString1[1].trim();
/*  956 */       if (str2.charAt(0) == '"') {
/*  957 */         str2 = str2.substring(1, str2.length() - 1);
/*      */       }
/*      */       
/*  960 */       if (str1.compareToIgnoreCase("enc_key") == 0) {
/*      */ 
/*      */         
/*  963 */         this.enc_key = str2;
/*  964 */         for (byte b1 = 0; b1 < this.enc_key_val.length; b1++) {
/*  965 */           String str = this.enc_key.substring(b1 * 2, b1 * 2 + 2);
/*      */           try {
/*  967 */             this.enc_key_val[b1] = (byte)Integer.parseInt(str, 16);
/*      */           }
/*      */           catch (NumberFormatException numberFormatException) {
/*      */             
/*  971 */             System.out.println("Failed to Parse enc_key");
/*      */           }
/*      */         
/*      */         }
/*      */       
/*  976 */       } else if (str1.compareToIgnoreCase("rc_port") == 0) {
/*  977 */         System.out.println("rc_port:" + str2);
/*  978 */         this.rc_port = str2;
/*      */       }
/*  980 */       else if (str1.compareToIgnoreCase("vm_key") == 0) {
/*      */         
/*  982 */         this.vm_key = str2;
/*      */       }
/*  984 */       else if (str1.compareToIgnoreCase("vm_port") == 0) {
/*  985 */         System.out.println("vm_port:" + str2);
/*  986 */         this.vm_port = str2;
/*      */       }
/*  988 */       else if (str1.equalsIgnoreCase("optional_features")) {
/*  989 */         System.out.println("optional_features:" + str2);
/*  990 */         this.optional_features = str2;
/*      */       }
/*  992 */       else if (str1.compareToIgnoreCase("server_name") == 0) {
/*  993 */         System.out.println("server_name:" + str2);
/*  994 */         this.server_name = str2;
/*      */       }
/*  996 */       else if (str1.compareToIgnoreCase("ilo_fqdn") == 0) {
/*  997 */         System.out.println("ilo_fqdn:" + str2);
/*  998 */         this.ilo_fqdn = str2;
/*      */       }
/* 1000 */       else if (str1.compareToIgnoreCase("blade") == 0) {
/* 1001 */         this.blade = Integer.parseInt(str2);
/* 1002 */         System.out.println("blade:" + this.blade);
/*      */       }
/* 1004 */       else if (this.blade == 1 && str1.compareToIgnoreCase("enclosure") == 0) {
/* 1005 */         if (!str2.equals("null")) {
/* 1006 */           this.in_enclosure = true;
/* 1007 */           System.out.println("enclosure:" + str2);
/* 1008 */           this.enclosure = str2;
/*      */         }
/*      */       
/* 1011 */       } else if (this.blade == 1 && str1.compareToIgnoreCase("bay") == 0) {
/* 1012 */         this.bay = Integer.parseInt(str2);
/* 1013 */         System.out.println("bay:" + this.bay);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void moveUItoInit(boolean paramBoolean) {
/* 1020 */     System.out.println("Disable Menus\n");
/* 1021 */     this.psMenu.setEnabled(paramBoolean);
/* 1022 */     this.vdMenu.setEnabled(paramBoolean);
/* 1023 */     this.kbMenu.setEnabled(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String rebrandToken(String paramString) {
/* 1029 */     if (!this.moniker_cached) {
/*      */       
/* 1031 */       String str1 = this.jsonObj.getJSONRequest("login_session");
/* 1032 */       if (str1 == null)
/*      */       {
/* 1034 */         return paramString;
/*      */       }
/* 1036 */       this.moniker = this.jsonObj.getJSONObject(str1, "moniker");
/* 1037 */       if (this.moniker == null)
/*      */       {
/* 1039 */         return paramString;
/*      */       }
/* 1041 */       this.moniker_cached = true;
/*      */     } 
/* 1043 */     String str = this.jsonObj.getJSONString(this.moniker, paramString);
/* 1044 */     if (str == "")
/*      */     {
/* 1046 */       return paramString;
/*      */     }
/* 1048 */     return str;
/*      */   }
/*      */ }


/* Location:              C:\Users\MARTIN\Downloads\intgapp4_232.jar!\com\hp\ilo2\intgapp\intgapp.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */