package legend.game;

import legend.core.gpu.RECT;
import legend.core.gpu.TimHeader;
import legend.core.gte.GsCOORDINATE2;
import legend.core.gte.GsDOBJ2;
import legend.core.gte.MATRIX;
import legend.core.gte.SVECTOR;
import legend.core.gte.TmdWithId;
import legend.core.gte.VECTOR;
import legend.core.memory.Method;
import legend.core.memory.Value;
import legend.core.memory.types.ArrayRef;
import legend.core.memory.types.ByteRef;
import legend.core.memory.types.ConsumerRef;
import legend.core.memory.types.Pointer;
import legend.core.memory.types.RunnableRef;
import legend.core.memory.types.ShortRef;
import legend.core.memory.types.UnboundedArrayRef;
import legend.core.memory.types.UnsignedIntRef;
import legend.game.types.DR_MODE;
import legend.game.types.GsOT_TAG;
import legend.game.types.GsRVIEW2;
import legend.game.types.TmdRenderingStruct;

import javax.annotation.Nullable;

import static legend.core.Hardware.CPU;
import static legend.core.Hardware.MEMORY;
import static legend.core.MemoryHelper.getMethodAddress;
import static legend.game.SItem._80111cfc;
import static legend.game.SItem._80111d20;
import static legend.game.Scus94491BpeSegment.FUN_80012b1c;
import static legend.game.Scus94491BpeSegment.FUN_80012bb4;
import static legend.game.Scus94491BpeSegment._1f8003c4;
import static legend.game.Scus94491BpeSegment._1f8003c8;
import static legend.game.Scus94491BpeSegment._1f8003cc;
import static legend.game.Scus94491BpeSegment.addToLinkedListTail;
import static legend.game.Scus94491BpeSegment.insertElementIntoLinkedList;
import static legend.game.Scus94491BpeSegment.linkedListAddress_1f8003d8;
import static legend.game.Scus94491BpeSegment.loadDrgnBinFile;
import static legend.game.Scus94491BpeSegment.playSound;
import static legend.game.Scus94491BpeSegment.removeFromLinkedList;
import static legend.game.Scus94491BpeSegment.rsin;
import static legend.game.Scus94491BpeSegment.scriptStartEffect;
import static legend.game.Scus94491BpeSegment.setWidthAndFlags;
import static legend.game.Scus94491BpeSegment.tags_1f8003d0;
import static legend.game.Scus94491BpeSegment_8002.FUN_80022590;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002379c;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002bcc8;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002bda4;
import static legend.game.Scus94491BpeSegment_8002.SetGeomOffset;
import static legend.game.Scus94491BpeSegment_8002.hasSavedGames;
import static legend.game.Scus94491BpeSegment_8003.DrawSync;
import static legend.game.Scus94491BpeSegment_8003.GsGetLws;
import static legend.game.Scus94491BpeSegment_8003.GsInitCoordinate2;
import static legend.game.Scus94491BpeSegment_8003.GsSetLightMatrix;
import static legend.game.Scus94491BpeSegment_8003.GsSetRefView2;
import static legend.game.Scus94491BpeSegment_8003.LoadImage;
import static legend.game.Scus94491BpeSegment_8003.RotMatrix_8003faf0;
import static legend.game.Scus94491BpeSegment_8003.ScaleMatrixL;
import static legend.game.Scus94491BpeSegment_8003.SetDrawMode;
import static legend.game.Scus94491BpeSegment_8003.StoreImage;
import static legend.game.Scus94491BpeSegment_8003.adjustTmdPointers;
import static legend.game.Scus94491BpeSegment_8003.gpuLinkedListSetCommandTextureUnshaded;
import static legend.game.Scus94491BpeSegment_8003.gpuLinkedListSetCommandTransparency;
import static legend.game.Scus94491BpeSegment_8003.parseTimHeader;
import static legend.game.Scus94491BpeSegment_8003.setProjectionPlaneDistance;
import static legend.game.Scus94491BpeSegment_8003.setRotTransMatrix;
import static legend.game.Scus94491BpeSegment_8003.updateTmdPacketIlen;
import static legend.game.Scus94491BpeSegment_8004._8004dd24;
import static legend.game.Scus94491BpeSegment_8004._8004ddc0;
import static legend.game.Scus94491BpeSegment_8004.additionOffsets_8004f5ac;
import static legend.game.Scus94491BpeSegment_8004.fileCount_8004ddc8;
import static legend.game.Scus94491BpeSegment_8004.setMonoOrStereo;
import static legend.game.Scus94491BpeSegment_8005.orderingTables_8005a370;
import static legend.game.Scus94491BpeSegment_8007.joypadInput_8007a39c;
import static legend.game.Scus94491BpeSegment_8007.joypadPress_8007a398;
import static legend.game.Scus94491BpeSegment_8007.vsyncMode_8007a3b8;
import static legend.game.Scus94491BpeSegment_800b._800bb114;
import static legend.game.Scus94491BpeSegment_800b._800bb116;
import static legend.game.Scus94491BpeSegment_800b._800bb120;
import static legend.game.Scus94491BpeSegment_800b._800bb134;
import static legend.game.Scus94491BpeSegment_800b._800bc05c;
import static legend.game.Scus94491BpeSegment_800b._800bdc34;
import static legend.game.Scus94491BpeSegment_800b._800bf0dc;
import static legend.game.Scus94491BpeSegment_800b._800bf0ec;
import static legend.game.Scus94491BpeSegment_800b.doubleBufferFrame_800bb108;
import static legend.game.Scus94491BpeSegment_800b.drgnBinIndex_800bc058;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.pregameLoadingStage_800bb10c;
import static legend.game.Scus94491BpeSegment_800b.whichMenu_800bdc38;
import static legend.game.Scus94491BpeSegment_800c.identityMatrix_800c3568;

public final class Ttle {
  private Ttle() { }

  public static final Pointer<TmdRenderingStruct> _800c66d0 = MEMORY.ref(4, 0x800c66d0L, Pointer.of(4, TmdRenderingStruct::new));
  public static final ArrayRef<UnsignedIntRef> _800c66d4 = MEMORY.ref(16, 0x800c66d4L, ArrayRef.of(UnsignedIntRef.class, 4, 4, UnsignedIntRef::new));
  public static final Value hasSavedGames_800c66e4 = MEMORY.ref(4, 0x800c66e4L);
  public static final Value menuLoadingStage_800c66e8 = MEMORY.ref(4, 0x800c66e8L);
  public static final Value logoFadeInAmount_800c66ec = MEMORY.ref(4, 0x800c66ecL);
  public static final Value logoFlashStage_800c66f0 = MEMORY.ref(4, 0x800c66f0L);
  public static final Value _800c66f4 = MEMORY.ref(4, 0x800c66f4L);
  public static final Value _800c66f8 = MEMORY.ref(4, 0x800c66f8L);
  public static final Value _800c66fc = MEMORY.ref(4, 0x800c66fcL);
  public static final Value _800c6700 = MEMORY.ref(2, 0x800c6700L);
  public static final Value _800c6702 = MEMORY.ref(2, 0x800c6702L);
  public static final Value backgroundInitialized_800c6704 = MEMORY.ref(4, 0x800c6704L);
  public static final Value backgroundScrollAmount_800c6708 = MEMORY.ref(4, 0x800c6708L);
  public static final Value backgroundFadeInAmount_800c670c = MEMORY.ref(4, 0x800c670cL);
  public static final Value copyrightInitialized_800c6710 = MEMORY.ref(4, 0x800c6710L);
  public static final Value copyrightFadeInAmount_800c6714 = MEMORY.ref(4, 0x800c6714L);
  public static final Value logoFireInitialized_800c6718 = MEMORY.ref(4, 0x800c6718L);
  public static final Value _800c671c = MEMORY.ref(4, 0x800c671cL);
  public static final Value menuIdleTime_800c6720 = MEMORY.ref(4, 0x800c6720L);
  public static final Value _800c6724 = MEMORY.ref(4, 0x800c6724L);
  public static final Value _800c6728 = MEMORY.ref(4, 0x800c6728L);
  public static final Value _800c672c = MEMORY.ref(4, 0x800c672cL);
  public static final ArrayRef<ShortRef> menuOptionTransparency_800c6730 = MEMORY.ref(2, 0x800c6730L, ArrayRef.of(ShortRef.class, 3, 2, ShortRef::new));

  public static final Value _800c6738 = MEMORY.ref(4, 0x800c6738L);
  public static final Value _800c673c = MEMORY.ref(2, 0x800c673cL);

  public static final Value _800c6748 = MEMORY.ref(4, 0x800c6748L);

  public static final Value _800c6754 = MEMORY.ref(4, 0x800c6754L);
  public static final Value _800c6758 = MEMORY.ref(4, 0x800c6758L);

  public static final GsRVIEW2 GsRVIEW2_800c6760 = MEMORY.ref(4, 0x800c6760L, GsRVIEW2::new);

  /**
   * <ol start="0">
   *   <li>{@link Ttle#FUN_800c7488()}</li>
   *   <li>{@link Ttle#FUN_800c74bc()}</li>
   *   <li>{@link Ttle#waitForTtleFilesToLoad()}</li>
   *   <li>{@link Ttle#FUN_800c7500()}</li>
   * </ol>
   */
  public static final ArrayRef<Pointer<RunnableRef>> loadingStageArray_800c6898 = MEMORY.ref(4, 0x800c6898L, ArrayRef.of(Pointer.classFor(RunnableRef.class), 4, 4, Pointer.of(4, RunnableRef::new)));

  public static final SVECTOR _800c68f0 = MEMORY.ref(8, 0x800c68f0L, SVECTOR::new);
  public static final VECTOR _800c68f8 = MEMORY.ref(16, 0x800c68f8L, VECTOR::new);

  public static final Value _800ce6c4 = MEMORY.ref(2, 0x800ce6c4L);

  public static final Value _800ce6d8 = MEMORY.ref(4, 0x800ce6d8L);

  public static final Value _800ce6fc = MEMORY.ref(1, 0x800ce6fcL);

  public static final Value _800ce758 = MEMORY.ref(1, 0x800ce758L);

  public static final Value _800ce76c = MEMORY.ref(2, 0x800ce76cL);

  public static final Value selectedMenuOption_800ce774 = MEMORY.ref(4, 0x800ce774L);
  public static final Value _800ce778 = MEMORY.ref(4, 0x800ce778L);
  /**
   * <ol start="0">
   *   <li>{@link Ttle#initializeMainMenu()}</li>
   *   <li>{@link Ttle#loadMainMenuGfx()}</li>
   *   <li>{@link Ttle#FUN_800c7df0()}</li>
   *   <li>{@link Ttle#renderMainMenu()}</li>
   *   <li>{@link Ttle#FUN_800c7e50()}</li>
   *   <li>{@link Ttle#FUN_800c7fa0()}</li>
   *   <li>{@link Ttle#FUN_800c8148()}</li>
   * </ol>
   */
  public static final ArrayRef<Pointer<RunnableRef>> loadingStageArray_800ce77c = MEMORY.ref(0x1c, 0x800ce77cL, ArrayRef.of(Pointer.classFor(RunnableRef.class), 7, 4, Pointer.of(4, RunnableRef::new)));

  public static final ArrayRef<RECT> rectArray_800ce798 = MEMORY.ref(24, 0x800ce798L, ArrayRef.of(RECT.class, 3, 8, RECT::new));

  public static final ArrayRef<ByteRef> _800ce7b0 = MEMORY.ref(4, 0x800ce7b0L, ArrayRef.of(ByteRef.class, 4, 1, ByteRef::new));
  public static final ArrayRef<Pointer<ConsumerRef<Long>>> callbacks_800ce7b4 = MEMORY.ref(4, 0x800ce7b4L, ArrayRef.of(Pointer.classFor(ConsumerRef.classFor(Long.class)), 17, 4, Pointer.of(4, ConsumerRef::new)));

  public static final Value _800ce7f8 = MEMORY.ref(1, 0x800ce7f8L);

  public static final Value _800ce840 = MEMORY.ref(1, 0x800ce840L);

  public static final Value _800ce8ac = MEMORY.ref(2, 0x800ce8acL);

  public static final ArrayRef<UnsignedIntRef> _800ce8f4 = MEMORY.ref(32, 0x800ce8f4L, ArrayRef.of(UnsignedIntRef.class, 8, 4, UnsignedIntRef::new));

  public static final Value _800ce914 = MEMORY.ref(2, 0x800ce914L);

  public static final Value _800ce91c = MEMORY.ref(2, 0x800ce91cL);
  public static final Value _800ce920 = MEMORY.ref(4, 0x800ce920L);

  @Method(0x800c7194L)
  public static void FUN_800c7194(final long unused) {
    long v0;
    long v1;
    long a0;
    long a1;
    long a2;
    long a3;
    long s0;
    long t2;
    long t3;
    long t4;
    long t5;
    long t6;
    long t7;
    long t8;
    long t9;

    v0 = gameState_800babc8.getAddress();
    t2 = v0;
    a0 = t2;
    a3 = 0;
    v1 = 0x14aL;
    a2 = gameState_800babc8.vibrationEnabled_4e1.get();
    a1 = gameState_800babc8.mono_4e0.get();

    //LAB_800c71c4
    do {
      MEMORY.ref(4, a0).setu(a3);
      a0 += 0x4L;
      v1--;
    } while(v1 >= 0);

    t9 = 0;
    t3 = 0;
    t5 = 0;
    a3 = t2;
    s0 = _800ce6d8.getAddress();
    t4 = s0;
    t8 = _800ce6c4.getAddress();
    t7 = _80111d20.getAddress();
    t6 = _80111cfc.getAddress();
    gameState_800babc8.vibrationEnabled_4e1.set((int)a2);
    gameState_800babc8.mono_4e0.set((int)a1);
    gameState_800babc8.indicatorMode_4e8.set(0x2L);
    gameState_800babc8.charIndex_88.get(0).set(0);
    gameState_800babc8.charIndex_88.get(1).set(-1);
    gameState_800babc8.charIndex_88.get(2).set(-1);

    //LAB_800c723c
    do {
      a2 = MEMORY.ref(2, t8).get();
      a1 = MEMORY.ref(4, t6).get();
      MEMORY.ref(4, a3).offset(0x32cL).setu(MEMORY.ref(4, t4).deref(4).offset(a2 * 0x4L));
      MEMORY.ref(2, a3).offset(0x334L).setu(MEMORY.ref(2, a1).offset(a2 * 0x8L));
      MEMORY.ref(2, a3).offset(0x336L).setu(MEMORY.ref(4, t7).deref(2).offset(0x8L));
      MEMORY.ref(2, a3).offset(0x338L).setu(0);
      MEMORY.ref(2, a3).offset(0x33aL).setu(0);
      MEMORY.ref(2, a3).offset(0x33cL).setu(0);
      MEMORY.ref(1, a3).offset(0x33eL).setu(a2);
      MEMORY.ref(1, a3).offset(0x33fL).setu(0x1L);

      //LAB_800c7294
      for(int i = 0; i < 0x8; i++) {
        v0 = i + t3 + t2;
        MEMORY.ref(1, v0).offset(0x346L).setu(0);
        MEMORY.ref(1, v0).offset(0x34eL).setu(0);
      }

      MEMORY.ref(1, a3).offset(0x346L).setu(0x1L);

      //LAB_800c72d4
      for(int i = 1; i < a2; i++) {
        final long a1_0 = MEMORY.ref(1, a1).offset(i * 0x8L).offset(0x2L).get();
        if(a1_0 != 0xffL) {
          v0 = additionOffsets_8004f5ac.get((int)t5).get();
          v0 = a1_0 - v0 + t3 + t2;
          MEMORY.ref(1, v0).offset(0x346L).setu(0x1L);
        }

        //LAB_800c72fc
      }

      //LAB_800c730c
      MEMORY.ref(1, a3).offset(0x345L).setu(_800ce758.offset(t5 * 0x2L));

      //LAB_800c7334
      for(int i = 0; i < 0x5; i++) {
        v0 = i + t3 + t2;
        MEMORY.ref(1, v0).offset(0x340L).setu(_800ce6fc.offset(t9).offset(i * 0x2L));
      }

      t9 += 0xaL;
      t3 += 0x2cL;
      t5++;
      a3 += 0x2cL;
      t4 += 0x4L;
      t8 += 0x2L;
      t7 += 0x4L;
      t6 += 0x4L;
      v0 = s0 + 0x24L;
    } while(t4 < v0);

    MEMORY.ref(4, t2).offset(0x330L).setu(0x23L);

    //LAB_800c7398
    for(int i = 0x100; i >= 0; i--) {
      MEMORY.ref(1, t2).offset(0x1e8L).offset(i).setu(0xffL);
    }

    MEMORY.ref(2, t2).offset(0x1e4L).setu(0);

    //LAB_800c73b8
    for(int i = 0x20; i >= 0; i--) {
      MEMORY.ref(1, t2).offset(0x2e9L).offset(i).setu(0xffL);
    }

    //LAB_800c73d8
    for(int i = 0; i < 0x21; i++) {
      a2 = _800ce76c.offset(i * 0x2L).get();
      if(a2 == 0xffL) {
        MEMORY.ref(2, t2).offset(0x1e6L).setu(i);
        break;
      }

      //LAB_800c73f0
      MEMORY.ref(1, t2).offset(0x2e9L).offset(i).setu(a2);
    }

    //LAB_800c7404
    MEMORY.ref(4, t2).offset(0x94L).setu(0x14L);
    FUN_80012bb4();
  }

  @Method(0x800c7424L)
  public static void executeTtleUnloadingStage() {
    loadingStageArray_800c6898.get((int)pregameLoadingStage_800bb10c.get()).deref().run();
  }

  @Method(0x800c7488L)
  public static void FUN_800c7488() {
    FUN_800c7524();
    vsyncMode_8007a3b8.setu(0);
    pregameLoadingStage_800bb10c.addu(0x1L);
  }

  @Method(0x800c74bcL)
  public static void FUN_800c74bc() {
    pregameLoadingStage_800bb10c.addu(0x1L);
  }

  @Method(0x800c74d4L)
  public static void waitForTtleFilesToLoad() {
    if(fileCount_8004ddc8.get() == 0) {
      pregameLoadingStage_800bb10c.addu(0x1L);
    }
  }

  @Method(0x800c7500L)
  public static void FUN_800c7500() {
    _8004dd24.setu(0x5L);
    vsyncMode_8007a3b8.setu(0x2L);
    pregameLoadingStage_800bb10c.setu(0);
  }

  @Method(0x800c7524L)
  public static void FUN_800c7524() {
    FUN_80012b1c(0x2L, getMethodAddress(Ttle.class, "FUN_800c7194", long.class), 0);
  }

  @Method(0x800c7798L)
  public static void executeTtleLoadingStage() {
    loadingStageArray_800ce77c.get((int)pregameLoadingStage_800bb10c.get()).deref().run();
  }

  @Method(0x800c77e4L)
  public static void initializeMainMenu() {
    menuLoadingStage_800c66e8.setu(0);
    menuIdleTime_800c6720.setu(0);
    _800c6728.setu(0);
    _800c672c.setu(0);
    _800c6738.setu(0);
    logoFadeInAmount_800c66ec.setu(0);
    backgroundInitialized_800c6704.setu(0);
    backgroundScrollAmount_800c6708.set(-176);
    copyrightInitialized_800c6710.setu(0);
    logoFireInitialized_800c6718.setu(0);
    logoFlashStage_800c66f0.setu(0);
    _800c6754.setu(0);
    _800c6758.setu(100L);

    hasSavedGames(0);

    hasSavedGames_800c66e4.setu(0);
    selectedMenuOption_800ce774.setu(0);
    _800ce778.setu(0);

    //LAB_800c7888
    for(int i = 0; i < 0x3L; i++) {
      //LAB_800c78a4
      _800c6748.offset(i * 4L).setu(addToLinkedListTail(rectArray_800ce798.get(i).w.get() * rectArray_800ce798.get(i).h.get() * 2L));
      StoreImage(rectArray_800ce798.get(i), _800c6748.offset(i * 4L).get());
    }

    //LAB_800c7978
    setWidthAndFlags(384L, 0);
    setProjectionPlaneDistance(320);
    GsRVIEW2_800c6760.viewpoint_00.setX(0);
    GsRVIEW2_800c6760.viewpoint_00.setY(0);
    GsRVIEW2_800c6760.viewpoint_00.setZ(2000);
    GsRVIEW2_800c6760.refpoint_0c.setX(0);
    GsRVIEW2_800c6760.refpoint_0c.setY(0);
    GsRVIEW2_800c6760.refpoint_0c.setZ(-4000);
    GsRVIEW2_800c6760.viewpointTwist_18.set(0);
    GsRVIEW2_800c6760.super_1c.clear();
    GsSetRefView2(GsRVIEW2_800c6760);

    vsyncMode_8007a3b8.setu(0x2L);
    pregameLoadingStage_800bb10c.setu(0x1L);
  }

  @Method(0x800c7a18L)
  public static void loadTimImage(final long ptr) {
    final TimHeader tim = parseTimHeader(MEMORY.ref(4, ptr).offset(0x4L));
    LoadImage(tim.getImageRect(), tim.getImageAddress());

    if(tim.hasClut()) {
      LoadImage(tim.getClutRect(), tim.getClutAddress());
    }
  }

  /**
   * Loads the MRG file @ sector 61510. All files are TIMs.
   *
   * <ol start="0">
   *   <li>Menu background (upper portion)</li>
   *   <li>Logo fire 1</li>
   *   <li>Logo</li>
   *   <li>Menu text</li>
   *   <li>TM</li>
   *   <li>Copyright (left half)</li>
   *   <li>Copyright (right half)</li>
   *   <li>Logo fire 2</li>
   *   <li>Logo fire 3 (same as 4)</li>
   *   <li>Logo fire 4 (same as 3)</li>
   *   <li>Menu background (lower portion)</li>
   * </ol>
   */
  @Method(0x800c7af0L)
  public static void menuTexturesMrgLoaded(final Value transferDest, final long fileSize, final long unknown) {
    for(int i = 0; i < transferDest.deref(4).offset(0x4L).get(); i++) {
      if(transferDest.deref(4).offset(i * 8L).offset(0xcL).get() != 0) {
        loadTimImage(transferDest.deref(4).offset(transferDest.deref(4).offset(i * 8L).offset(0x8L)).getAddress());
        DrawSync(0);
      }
    }

    //LAB_800c7bd0
    removeFromLinkedList(transferDest.get());
    _800c6724.addu(1);
  }

  @Method(0x800c7c18L)
  public static void menuFireTmdLoaded(final Value tmdAddressPtr, final long fileSize, final long unknown) {
    final TmdWithId tmd = tmdAddressPtr.deref(4).cast(TmdWithId::new);
    _800c66d0.set(parseTmdFile(tmd));
    FUN_800cc0b0(_800c66d0.deref(), null);
    _800c66d0.deref().tmd_0c.set(tmd);
    setDobjAttributes(_800c66d0.deref(), 0);
    _800c6724.addu(1L);
  }

  @Method(0x800c7cacL)
  public static void loadMainMenuGfx() {
    _800c6724.setu(0);

    // MRG @ sector 61510
    loadDrgnBinFile(0, 0x1656L, 0, getMethodAddress(Ttle.class, "menuTexturesMrgLoaded", Value.class, long.class, long.class), 0, 0x4L);

    // TMD @ sector 61622
    loadDrgnBinFile(0, 0x1657L, 0, getMethodAddress(Ttle.class, "menuFireTmdLoaded", Value.class, long.class, long.class), 0, 0x2L);

    pregameLoadingStage_800bb10c.setu(0x2L);

    // Prepare fire animation struct

    //LAB_800c7d30
    for(int i = 0; i < 4; i++) {
      //LAB_800c7d4c
      final RECT rect = new RECT((short)(944 + i * 16), (short)256, (short)64, (short)64);
      _800c66d4.get(i).set(FUN_800cdaa0(rect, 0, 0x1L, _800ce7b0.get(i).getUnsigned()));
    }

    //LAB_800c7ddc
  }

  @Method(0x800c7df0L)
  public static void FUN_800c7df0() {
    if(_800c6724.get() == 0x2L) {
      scriptStartEffect(0x2L, 0xfL);
      SetGeomOffset(0, 0);
      pregameLoadingStage_800bb10c.setu(0x3L);
    }
  }

  @Method(0x800c7e50L)
  public static void FUN_800c7e50() {
    if(_800c6754.get() == 0) {
      scriptStartEffect(0x1L, 0xfL);
    }

    //LAB_800c7e7c
    renderMenuLogo();
    renderMenuOptions();
    FUN_800c959c();
    renderMenuLogoFire();
    renderMenuBackground();
    renderCopyright();

    _800c6754.addu(0x1L);
    if(_800c6754.get() >= 0x10L) {
      FUN_800cb5c4();
      deallocateFire();

      if(drgnBinIndex_800bc058.get() == 0x1L) {
        _8004dd24.setu(0x9L);
      } else {
        //LAB_800c7f40
        _8004dd24.setu(0xaL);
        _8004ddc0.setu(0x1L);
        _800bc05c.setu(0x9L);
      }

      _800bf0dc.setu(0x2L);
      _800bf0ec.setu(0x3L);
      vsyncMode_8007a3b8.setu(0x2L);

      pregameLoadingStage_800bb10c.setu(0);
    }

    //LAB_800c7f90
  }

  @Method(0x800c7fa0L)
  public static void FUN_800c7fa0() {
    if(_800c6754.get() == 0) {
      scriptStartEffect(0x1L, 0xfL);
    }

    //LAB_800c7fcc
    _800c6754.addu(0x1L);

    if(_800c6754.get() >= 0x10L) {
      if(_800c6728.get() == 0x2L) {
        whichMenu_800bdc38.setu(0xbL);
        FUN_800cb5c4();
        deallocateFire();
        _800c6728.setu(0x3L);
      }
    }

    //LAB_800c8038
    FUN_80022590();

    if(whichMenu_800bdc38.get() != 0) {
      return;
    }

    if(_800bdc34.get() != 0) {
      if(gameState_800babc8._4e4.get() != 0) {
        _8004dd24.setu(0x8L);
      } else {
        //LAB_800c80a4
        _8004dd24.setu(0x5L);
      }

      pregameLoadingStage_800bb10c.setu(0);
      vsyncMode_8007a3b8.setu(0x2L);

      //LAB_800c80c4
      return;
    }

    //LAB_800c80cc
    if(_800c6728.get() == 0x3L) {
      _8004dd24.setu(0x2L);
      pregameLoadingStage_800bb10c.setu(0);
      vsyncMode_8007a3b8.setu(0x2L);
    } else {
      //LAB_800c8108
      renderMenuLogo();
      renderMenuOptions();
      FUN_800c959c();
      renderMenuLogoFire();
      renderMenuBackground();
      renderCopyright();
    }

    //LAB_800c8138
  }

  @Method(0x800c8148L)
  public static void FUN_800c8148() {
    if(_800c6754.get() == 0) {
      scriptStartEffect(0x1L, 0xfL);
    }

    //LAB_800c8174
    renderMenuLogo();
    renderMenuOptions();
    FUN_800c959c();
    renderMenuLogoFire();
    renderMenuBackground();
    renderCopyright();

    _800c6754.addu(0x1L);
    if(_800c6754.get() > 0xfL) {
      FUN_800cb5c4();
      deallocateFire();

      _800bf0dc.setu(0);
      _800bf0ec.setu(0x2L);
      _8004dd24.setu(0x9L);
      vsyncMode_8007a3b8.setu(0x2L);

      pregameLoadingStage_800bb10c.setu(0);
    }

    //LAB_800c8218
  }

  @Method(0x800c8228L)
  public static void setCommand(final long a0, final long entryAddress, final boolean transparency, final boolean unshaded) {
    callbacks_800ce7b4.get((int)a0).deref().run(entryAddress);
    gpuLinkedListSetCommandTransparency(entryAddress, transparency);
    gpuLinkedListSetCommandTextureUnshaded(entryAddress, unshaded);
  }

  @Method(0x800c8298L)
  public static void renderMainMenu() {
    final long menuLoadingStage = menuLoadingStage_800c66e8.get();

    if(menuLoadingStage == 0) {
      //LAB_800c82f0
      if(backgroundScrollAmount_800c6708.getSigned() > -40L) {
        menuLoadingStage_800c66e8.setu(0x1L);
      }

      //LAB_800c82d0
      //LAB_800c8314
    } else if(menuLoadingStage == 0x1L) {
      //LAB_800c831c
      renderMenuLogo();

      if(logoFadeInAmount_800c66ec.get() >= 0x80L) {
        menuLoadingStage_800c66e8.setu(0x2L);
      }

      //LAB_800c8348
    } else if(menuLoadingStage == 0x2L) {
      //LAB_800c8350
      renderMenuLogo();
      renderLogoFlash();

      if(logoFlashStage_800c66f0.get() == 0x2L) {
        menuLoadingStage_800c66e8.setu(0x3L);
      }

      //LAB_800c8380
    } else if(menuLoadingStage == 0x3L) {
      //LAB_800c8388
      FUN_800c93b0();
      FUN_800c8484();
      renderMenuLogo();
      renderMenuOptions();
      FUN_800c959c();
      renderMenuLogoFire();
      renderCopyright();
    }

    //LAB_800c83c8
    renderMenuBackground();

    if(_800c6728.get() != 0x1L) {
      menuIdleTime_800c6720.addu(0x2L);

      if(menuIdleTime_800c6720.get() > 0x690L) {
        if(fileCount_8004ddc8.get() == 0) {
          if(drgnBinIndex_800bc058.get() == 0x1L) {
            pregameLoadingStage_800bb10c.setu(0x6L);
          }
        }
      }
    }

    //LAB_800c8448
    if(joypadInput_8007a39c.get(0xf9ffL) != 0) {
      menuLoadingStage_800c66e8.setu(0x3L);
      menuIdleTime_800c6720.setu(0);
    }

    //LAB_800c8474
  }

  @Method(0x800c8484L)
  public static void FUN_800c8484() {
    if(_800c672c.get() < 3) {
      if(joypadPress_8007a398.get(0x20L) == 0 || fileCount_8004ddc8.get() != 0) {
        if(joypadPress_8007a398.get(0x1000L) != 0) { // Menu button up
          playSound(0, 1, 0, 0, (short)0, (short)0);

          selectedMenuOption_800ce774.subu(1);
          if(selectedMenuOption_800ce774.getSigned() < 0) {
            selectedMenuOption_800ce774.setu(2);
          }
          _800c672c.setu(2);
        }

        if(joypadPress_8007a398.get(0x4000L) != 0) { // Menu button down
          playSound(0, 1, 0, 0, (short)0, (short)0);

          selectedMenuOption_800ce774.addu(1);
          if(selectedMenuOption_800ce774.get() > 2) {
            selectedMenuOption_800ce774.setu(0);
          }
          _800c672c.setu(2);
        }
      } else { // Menu button X
        playSound(0, 2, 0, 0, (short)0, (short)0);

        _800c672c.setu(3);
        if(selectedMenuOption_800ce774.get() == 2) {
          _800c6738.setu(0);
          _800ce778.setu(0);
          _800c6728.setu(1);
        }
      }
    }
  }

  @Method(0x800c8634L)
  public static void renderMenuOptions() {
    if(hasSavedGames_800c66e4.get() == 0) {
      hasSavedGames_800c66e4.setu(hasSavedGames(0x1L));
      selectedMenuOption_800ce774.setu(hasSavedGames_800c66e4.get() == 1 ? 1 : 0);
      return;
    }

    //LAB_800c868c
    switch((int)_800c672c.get()) {
      case 0x0:
        //LAB_800c86d8
        for(int i = 0; i < 0x3L; i++) {
          //LAB_800c86f4
          menuOptionTransparency_800c6730.get(i).set((short)0);
        }

        //LAB_800c8728
        _800c672c.setu(0x1L);
        break;

      case 0x1:
        //LAB_800c8740
        //LAB_800c886c
        for(int i = 0; i < 0x3L; i++) {
          //LAB_800c875c
          menuOptionTransparency_800c6730.get(i).add((short)0x4);
          if(selectedMenuOption_800ce774.get() == i) {
            if(menuOptionTransparency_800c6730.get(i).get() > 0xb0) {
              menuOptionTransparency_800c6730.get(i).set((short)0xb0);
            }

            //LAB_800c8800
            //LAB_800c8808
          } else if(menuOptionTransparency_800c6730.get(i).get() > 0x40) {
            menuOptionTransparency_800c6730.get(i).set((short)0x40);
          }

          //LAB_800c8854
        }

        break;

      case 0x2:
        //LAB_800c8878
        //LAB_800c89e4
        for(int i = 0; i < 0x3L; i++) {
          //LAB_800c8894
          if(selectedMenuOption_800ce774.get() == i) {
            // Fade in selected item

            menuOptionTransparency_800c6730.get(i).add((short)0x8);
            if(menuOptionTransparency_800c6730.get(i).get() > 0xa0) {
              menuOptionTransparency_800c6730.get(i).set((short)0xa0);
            }

            //LAB_800c8938
          } else {
            // Fade out unselected items

            //LAB_800c8940
            menuOptionTransparency_800c6730.get(i).sub((short)0x10);
            if(menuOptionTransparency_800c6730.get(i).get() < 0x40) {
              menuOptionTransparency_800c6730.get(i).set((short)0x40);
            }
          }

          //LAB_800c89cc
        }

        break;

      case 0x3:
        _800c672c.setu(0x4L);
        if(selectedMenuOption_800ce774.get() == 0) {
          pregameLoadingStage_800bb10c.setu(0x4L);
          //LAB_800c8a20
        } else if(selectedMenuOption_800ce774.get() == 0x1L) {
          _800c6728.setu(0x2L);
          pregameLoadingStage_800bb10c.setu(0x5L);
        }

        //LAB_800c8a4c
        break;

      case 0x4:
        return;
    }

    long sp10 = linkedListAddress_1f8003d8.get();

    //LAB_800c8a70
    for(int i = 0; i < 0x3L; i++) {
      final long x = _800ce8ac.offset(i * 8L).get();
      final long y = _800ce8ac.offset((i * 2L + 0x1L) * 4).get();

      //LAB_800c8a8c
      setCommand(0xcL, sp10, true, false); // 0x2c - 9 words; textured 4-point poly, opaque, blended
      MEMORY.ref(1, sp10).offset(0x04L).setu(menuOptionTransparency_800c6730.get(i).get()); // R
      MEMORY.ref(1, sp10).offset(0x05L).setu(menuOptionTransparency_800c6730.get(i).get()); // G
      MEMORY.ref(1, sp10).offset(0x06L).setu(menuOptionTransparency_800c6730.get(i).get()); // B

      // Vertex 0
      MEMORY.ref(2, sp10).offset(0x08L).setu(x); // X
      MEMORY.ref(2, sp10).offset(0x0aL).setu(y); // Y
      MEMORY.ref(1, sp10).offset(0x0cL).setu(0); // U
      MEMORY.ref(1, sp10).offset(0x0dL).setu(_800ce7f8.offset(i * 8L)); // V

      // CLUT
      if(selectedMenuOption_800ce774.get() == i) {
        MEMORY.ref(2, sp10).offset(0xeL).setu(360L);
      } else {
        MEMORY.ref(2, sp10).offset(0xeL).setu(168L);
      }

      // Vertex 1
      MEMORY.ref(2, sp10).offset(0x10L).setu(x + _800ce7f8.offset(2, (i * 2L + 0x1L) * 4).get()); // X
      MEMORY.ref(2, sp10).offset(0x12L).setu(y); // Y
      MEMORY.ref(1, sp10).offset(0x14L).setu(_800ce7f8.offset((i * 2L + 0x1L) * 4)); // U
      MEMORY.ref(1, sp10).offset(0x15L).setu(_800ce7f8.offset(i * 8L)); // V
      MEMORY.ref(2, sp10).offset(0x16L).setu(_800bb114.get() | 0x9L); // Page

      // Vertex 2
      MEMORY.ref(2, sp10).offset(0x18L).setu(x); // X
      MEMORY.ref(2, sp10).offset(0x1aL).setu(y + 0x10L); // Y
      MEMORY.ref(1, sp10).offset(0x1cL).setu(0); // U
      MEMORY.ref(1, sp10).offset(0x1dL).setu(_800ce7f8.offset(i * 8L).get() + 0x10L); // V

      // Vertex 3
      MEMORY.ref(2, sp10).offset(0x20L).setu(x + _800ce7f8.offset(2, (i * 2L + 0x1L) * 4).get()); // X
      MEMORY.ref(2, sp10).offset(0x22L).setu(y + 0x10L); // Y
      MEMORY.ref(1, sp10).offset(0x24L).setu(_800ce7f8.offset((i * 2L + 0x1L) * 4)); // U
      MEMORY.ref(1, sp10).offset(0x25L).setu(_800ce7f8.offset(i * 8L).get() + 0x10L); // V

      insertElementIntoLinkedList(tags_1f8003d0.deref().get(100).getAddress(), sp10);
      sp10 += 0x28L;

      setCommand(0xcL, sp10, true, false);
      MEMORY.ref(1, sp10).offset(0x04L).setu(menuOptionTransparency_800c6730.get(i).get());
      MEMORY.ref(1, sp10).offset(0x05L).setu(menuOptionTransparency_800c6730.get(i).get());
      MEMORY.ref(1, sp10).offset(0x06L).setu(menuOptionTransparency_800c6730.get(i).get());

      // Vertex 0
      MEMORY.ref(2, sp10).offset(0x08L).setu(x - 0x8L);
      MEMORY.ref(2, sp10).offset(0x0aL).setu(y - 0x8L);
      MEMORY.ref(1, sp10).offset(0x0cL).setu(_800ce840.offset(i * 3L * 4L));
      MEMORY.ref(1, sp10).offset(0x0dL).setu(_800ce840.offset((i * 3L + 0x1L) * 4));
      MEMORY.ref(2, sp10).offset(0x0eL).setu(0x128L);

      // Vertex 1
      MEMORY.ref(2, sp10).offset(0x10L).setu(x + _800ce840.offset((i * 3L + 0x2L) * 4).get() - 0x8L);
      MEMORY.ref(2, sp10).offset(0x12L).setu(y - 0x8L);
      MEMORY.ref(1, sp10).offset(0x14L).setu(_800ce840.offset(i * 3L * 4L).get() + _800ce840.offset((i * 3L + 0x2L) * 4).get());
      MEMORY.ref(1, sp10).offset(0x15L).setu(_800ce840.offset((i * 3L + 0x1L) * 4).get());
      MEMORY.ref(2, sp10).offset(0x16L).setu(_800bb114.get() | 0x9L);

      // Vertex 2
      MEMORY.ref(2, sp10).offset(0x18L).setu(x - 0x8L);
      MEMORY.ref(2, sp10).offset(0x1aL).setu(y + 0x17L);
      MEMORY.ref(1, sp10).offset(0x1cL).setu(_800ce840.offset(i * 3L * 4L));
      MEMORY.ref(1, sp10).offset(0x1dL).setu(_800ce840.offset((i * 3L + 0x1L) * 4).get() + 0x20L);

      // Vertex 3
      MEMORY.ref(2, sp10).offset(0x20L).setu(x + _800ce840.offset((i * 3L + 0x2L) * 4).get() - 0x8L);
      MEMORY.ref(2, sp10).offset(0x22L).setu(y + 0x17L);
      MEMORY.ref(1, sp10).offset(0x24L).setu(_800ce840.offset(i * 3L * 4L).get() + _800ce840.offset((i * 3L + 0x2L) * 4).get());
      MEMORY.ref(1, sp10).offset(0x25L).setu(_800ce840.offset((i * 3L + 0x1L) * 4).get() + 0x20L);

      insertElementIntoLinkedList(tags_1f8003d0.deref().get(100).getAddress(), sp10);
      sp10 += 0x28L;
    }

    //LAB_800c9390
    linkedListAddress_1f8003d8.setu(sp10);

    //LAB_800c939c
  }

  @Method(0x800c93b0L)
  public static void FUN_800c93b0() {
    if(_800c6728.get() == 1 && _800c6738.get() < 3) {
      if(joypadPress_8007a398.get(0x5000L) != 0) {
        playSound(0, 1, 0, 0, (short)0, (short)0);
        _800ce778.xoru(0b11);
        _800c6738.setu(2);
      }

      if(joypadPress_8007a398.get(0x40L) != 0) {
        playSound(0, 3, 0, 0, (short)0, (short)0);
        _800c6738.setu(3);
        _800c672c.setu(0);
      }

      if(joypadPress_8007a398.get(0xa000L) != 0) {
        playSound(0, 1, 0, 0, (short)0, (short)0);

        if(_800ce778.get() == 0) {
          gameState_800babc8.mono_4e0.xor(0b1);
          setMonoOrStereo(gameState_800babc8.mono_4e0.get());
        } else {
          gameState_800babc8.vibrationEnabled_4e1.xor(0b1);
          FUN_8002379c();

          if(gameState_800babc8.vibrationEnabled_4e1.get() != 0) {
            FUN_8002bcc8(0, 0x100);
            FUN_8002bda4(0, 0, 0x3c);
          }
        }

        _800c6738.setu(2);
      }
    }
  }

  @Method(0x800c959cL)
  public static void FUN_800c959c() {
    if(_800c6728.get() != 0x1L) {
      return;
    }

    //LAB_800c95c4
    long sp18 = gameState_800babc8.mono_4e0.get() + 0x1L;

    long sp1c;
    if(gameState_800babc8.vibrationEnabled_4e1.get() == 0) {
      //LAB_800c95f8
      sp1c = 0x5L;
    } else {
      sp1c = 0x4L;
    }

    //LAB_800c95fc
    switch((int)_800c6738.get()) {
      case 0:
        //LAB_800c964c
        for(int i = 0; i < 0x6L; i++) {
          //LAB_800c9668
          _800c673c.offset(i * 2L).setu(0);
        }

        //LAB_800c969c
        _800c6738.setu(0x1L);
        break;

      case 0x1:
        //LAB_800c96b4
        for(int i = 0; i < 0x6L; i++) {
          //LAB_800c96d0
          _800c673c.offset(i * 2L).addu(0x10L);
          if(_800ce778.get() == i || sp18 == i || sp1c == i) {
            //LAB_800c9758
            if(_800c673c.offset(i * 2L).get() > 0xb0L) {
              _800c673c.offset(i * 2L).setu(0xb0L);
            }

            //LAB_800c97a4
          } else {
            //LAB_800c97ac
            if(_800c673c.offset(i * 2L).get() > 0x40L) {
              _800c673c.offset(i * 2L).setu(0x40L);
            }
          }

          //LAB_800c97f8
        }

        //LAB_800c9810
        break;

      case 0x2:
        //LAB_800c981c
        for(int i = 0; i < 0x6L; i++) {
          //LAB_800c9838
          if(_800ce778.get() == i || sp18 == i || sp1c == i) {
            //LAB_800c9880
            _800c673c.offset(i * 2L).addu(0x8L);
            if(_800c673c.offset(i * 2L).get() > 0xa0L) {
              _800c673c.offset(i * 2L).setu(0xa0L);
            }

            //LAB_800c990c
          } else {
            //LAB_800c9914
            _800c673c.offset(i * 2L).subu(0x10L);
            if(_800c673c.offset(i * 2L).get() < 0x40L) {
              _800c673c.offset(i * 2L).setu(0x40L);
            }
          }

          //LAB_800c99a0
        }

        //LAB_800c99b8
        break;

      case 0x3:
        //LAB_800c99c4
        for(int i = 0; i < 0x6L; i++) {
          //LAB_800c99e0
          if(_800ce778.get() == i || sp18 == i || sp1c == i) {
            //LAB_800c9a28
            _800c673c.offset(i * 2L).subu(0x20L);
          } else {
            //LAB_800c9a70
            _800c673c.offset(i * 2L).subu(0x8L);
          }

          //LAB_800c9ab0
          if(_800c673c.offset(i * 2L).getSigned() < 0) {
            _800c673c.offset(i * 2L).setu(0);
          }

          //LAB_800c9af4
          if(_800c673c.offset(_800ce778.get() * 2).get() == 0) {
            _800c6738.setu(0x4L);
            _800c6728.setu(0);
          }

          //LAB_800c9b34
        }

        //LAB_800c9b4c
        break;

      case 0x4:
        return;
    }

    long address = linkedListAddress_1f8003d8.get();

    //LAB_800c9b70
    for(int i = 0; i < 0x6L; i++) {
      //LAB_800c9b8c
      setCommand(0xcL, address, true, false);

      //LAB_800c9c00
      MEMORY.ref(1, address).offset(0x04L).setu(_800c673c.offset(i * 2L));
      MEMORY.ref(1, address).offset(0x05L).setu(_800c673c.offset(i * 2L));
      MEMORY.ref(1, address).offset(0x06L).setu(_800c673c.offset(i * 2L));

      MEMORY.ref(2, address).offset(0x08L).setu(_800ce8ac.offset((i + 0x3L) * 8));
      MEMORY.ref(2, address).offset(0x0aL).setu(_800ce8ac.offset(((i + 0x3L) * 2 + 0x1L) * 4));
      MEMORY.ref(1, address).offset(0x0cL).setu(0);
      MEMORY.ref(1, address).offset(0x0dL).setu(_800ce7f8.offset((i + 0x3L) * 8));

      if(_800ce778.get() == i) {
        MEMORY.ref(2, address).offset(0xeL).setu(0x168L);
      } else {
        //LAB_800c9bf4
        MEMORY.ref(2, address).offset(0xeL).setu(0xa8L);
      }

      MEMORY.ref(2, address).offset(0x10L).setu(_800ce8ac.offset((i + 0x3L) * 8).get() + _800ce7f8.offset(((i + 0x3L) * 2 + 0x1L) * 4).get());
      MEMORY.ref(2, address).offset(0x12L).setu(_800ce8ac.offset(((i + 0x3L) * 2 + 0x1L) * 4));
      MEMORY.ref(1, address).offset(0x14L).setu(_800ce7f8.offset(((i + 0x3L) * 2 + 0x1L) * 4));
      MEMORY.ref(1, address).offset(0x15L).setu(_800ce7f8.offset((i + 0x3L) * 8));
      MEMORY.ref(2, address).offset(0x16L).setu(_800bb114.get() | 0x9L);

      MEMORY.ref(2, address).offset(0x18L).setu(_800ce8ac.offset((i + 0x3L) * 8));
      MEMORY.ref(2, address).offset(0x1aL).setu(_800ce8ac.offset(((i + 0x3L) * 2 + 0x1L) * 4).get() + 0x10L);
      MEMORY.ref(1, address).offset(0x1cL).setu(0);
      MEMORY.ref(1, address).offset(0x1dL).setu(_800ce7f8.offset((i + 0x3L) * 8).get() + 0x10L);

      MEMORY.ref(2, address).offset(0x20L).setu(_800ce8ac.offset((i + 0x3L) * 8).get() + _800ce7f8.offset(((i + 0x3L) * 2 + 0x1L) * 4).get());
      MEMORY.ref(2, address).offset(0x22L).setu(_800ce8ac.offset(((i + 0x3L) * 2 + 0x1L) * 4).get() + 0x10L);
      MEMORY.ref(1, address).offset(0x24L).setu(_800ce7f8.offset(((i + 0x3L) * 2 + 0x1L) * 4));
      MEMORY.ref(1, address).offset(0x25L).setu(_800ce7f8.offset((i + 0x3L) * 8).get() + 0x10L);

      insertElementIntoLinkedList(tags_1f8003d0.deref().get(100).getAddress(), address);
      address += 0x28L;
    }

    //LAB_800c9ff8
    //LAB_800c9ffc
    for(int i = 0; i < 0x2L; i++) {
      //LAB_800ca018
      sp18 = i * 3L;
      setCommand(0xcL, address, true, false);
      MEMORY.ref(1, address).offset(0x4L).setu(_800c673c.offset(sp18 * 2));
      MEMORY.ref(1, address).offset(0x5L).setu(_800c673c.offset(sp18 * 2));
      MEMORY.ref(1, address).offset(0x6L).setu(_800c673c.offset(sp18 * 2));

      MEMORY.ref(2, address).offset(0x8L).setu(_800ce8ac.offset((sp18 + 0x3L) * 8).get() - 0x8L);
      MEMORY.ref(2, address).offset(0xaL).setu(_800ce8ac.offset(((sp18 + 0x3L) * 2 + 0x1L) * 4).get() - 0x9L);
      MEMORY.ref(1, address).offset(0xcL).setu(_800ce840.offset((sp18 + 0x3L) * 3 * 4));
      MEMORY.ref(1, address).offset(0xdL).setu(_800ce840.offset(((sp18 + 0x3L) * 3 + 0x1L) * 4));
      MEMORY.ref(2, address).offset(0xeL).setu(0x128L);

      MEMORY.ref(2, address).offset(0x10L).setu(_800ce8ac.offset((sp18 + 0x3L) * 8).get() + _800ce840.offset(((sp18 + 0x3L) * 3 + 0x2L) * 4).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x12L).setu(_800ce8ac.offset(((sp18 + 0x3L) * 2 + 0x1L) * 4).get() - 0x9L);
      MEMORY.ref(1, address).offset(0x14L).setu(_800ce840.offset((sp18 + 0x3L) * 3 * 4).get() + _800ce840.offset(((sp18 + 0x3L) * 3 + 0x2L) * 4).get());
      MEMORY.ref(1, address).offset(0x15L).setu(_800ce840.offset(((sp18 + 0x3L) * 3 + 0x1L) * 4));
      MEMORY.ref(2, address).offset(0x16L).setu(_800bb114.get() | 0x9L);

      MEMORY.ref(2, address).offset(0x18L).setu(_800ce8ac.offset((sp18 + 0x3L) * 8).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x1aL).setu(_800ce8ac.offset(((sp18 + 0x3L) * 2 + 0x1L) * 4).get() + 0x17L);
      MEMORY.ref(1, address).offset(0x1cL).setu(_800ce840.offset((sp18 + 0x3L) * 3 * 4));
      MEMORY.ref(1, address).offset(0x1dL).setu(_800ce840.offset(((sp18 + 0x3L) * 3 + 0x1L) * 4).get() + 0x1fL);

      MEMORY.ref(2, address).offset(0x20L).setu(_800ce8ac.offset((sp18 + 0x3L) * 8).get() + _800ce840.offset(((sp18 + 0x3L) * 3 + 0x2L) * 4).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x22L).setu(_800ce8ac.offset(((sp18 + 0x3L) * 2 + 0x1L) * 4).get() + 0x17L);
      MEMORY.ref(1, address).offset(0x24L).setu(_800ce840.offset((sp18 + 0x3L) * 3 * 4).get() + _800ce840.offset(((sp18 + 0x3L) * 3 + 0x2L) * 4).get());
      MEMORY.ref(1, address).offset(0x25L).setu(_800ce840.offset(((sp18 + 0x3L) * 3 + 0x1L) * 4).get() + 0x1fL);

      insertElementIntoLinkedList(tags_1f8003d0.deref().get(100).getAddress(), address);
      address += 0x28L;
    }

    //LAB_800ca57c
    //LAB_800ca580
    for(int i = 0; i < 0x2L; i++) {
      //LAB_800ca59c
      final long sp14;
      if(i == 0) {
        sp14 = gameState_800babc8.mono_4e0.get() + 0x4L;
        sp1c = gameState_800babc8.mono_4e0.get() + 0x1L;
      } else {
        //LAB_800ca5dc
        if(gameState_800babc8.vibrationEnabled_4e1.get() == 0) {
          //LAB_800ca5fc
          sp14 = 0x8L;
        } else {
          sp14 = 0x7L;
        }

        //LAB_800ca600
        if(gameState_800babc8.vibrationEnabled_4e1.get() == 0) {
          //LAB_800ca624
          sp1c = 0x5L;
        } else {
          sp1c = 0x4L;
        }

        //LAB_800ca628
      }

      //LAB_800ca62c
      setCommand(0xcL, address, true, false);
      MEMORY.ref(1, address).offset(0x04L).setu(_800c673c.offset(sp1c * 2));
      MEMORY.ref(1, address).offset(0x05L).setu(_800c673c.offset(sp1c * 2));
      MEMORY.ref(1, address).offset(0x06L).setu(_800c673c.offset(sp1c * 2));

      MEMORY.ref(2, address).offset(0x08L).setu(_800ce8ac.offset(sp14 * 8).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x0aL).setu(_800ce8ac.offset((sp14 * 2 + 0x1L) * 4).get() - 0x9L);
      MEMORY.ref(1, address).offset(0x0cL).setu(_800ce840.offset(sp14 * 3 * 4));
      MEMORY.ref(1, address).offset(0x0dL).setu(_800ce840.offset((sp14 * 3 + 0x1L) * 4));
      MEMORY.ref(2, address).offset(0x0eL).setu(0x128L);

      MEMORY.ref(2, address).offset(0x10L).setu(_800ce8ac.offset(sp14 * 8).get() + _800ce840.offset((sp14 * 3 + 0x2L) * 4).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x12L).setu(_800ce8ac.offset((sp14 * 2 + 0x1L) * 4).get() - 0x9L);
      MEMORY.ref(1, address).offset(0x14L).setu(_800ce840.offset(sp14 * 3 * 4).get() + _800ce840.offset((sp14 * 3 + 0x2L) * 4).get());
      MEMORY.ref(1, address).offset(0x15L).setu(_800ce840.offset((sp14 * 3 + 0x1L) * 4));
      MEMORY.ref(2, address).offset(0x16L).setu(_800bb114.get() | 0x9L);

      MEMORY.ref(2, address).offset(0x18L).setu(_800ce8ac.offset(sp14 * 8).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x1aL).setu(_800ce8ac.offset((sp14 * 2 + 0x1L) * 4).get() + 0x17L);
      MEMORY.ref(1, address).offset(0x1cL).setu(_800ce840.offset(sp14 * 3 * 4));
      MEMORY.ref(1, address).offset(0x1dL).setu(_800ce840.offset((sp14 * 3 + 0x1L) * 4).get() + 0x1fL);

      MEMORY.ref(2, address).offset(0x20L).setu(_800ce8ac.offset(sp14 * 8).get() + _800ce840.offset((sp14 * 3 + 0x2L) * 4).get() - 0x8L);
      MEMORY.ref(2, address).offset(0x22L).setu(_800ce8ac.offset((sp14 * 2 + 0x1L) * 4).get() + 0x17L);
      MEMORY.ref(1, address).offset(0x24L).setu(_800ce840.offset(sp14 * 3 * 4).get() + _800ce840.offset((sp14 * 3 + 0x2L) * 4).get());
      MEMORY.ref(1, address).offset(0x25L).setu(_800ce840.offset((sp14 * 3 + 0x1L) * 4).get() + 0x1fL);

      insertElementIntoLinkedList(tags_1f8003d0.deref().get(100).getAddress(), address);
      address += 0x28L;
    }

    //LAB_800cab28
    linkedListAddress_1f8003d8.setu(address);

    //LAB_800cab34
  }

  @Method(0x800cab48L)
  public static void renderCopyright() {
    if(copyrightInitialized_800c6710.get() == 0) {
      copyrightFadeInAmount_800c6714.setu(0);
      copyrightInitialized_800c6710.setu(0x1L);
    }

    //LAB_800cab7c
    copyrightFadeInAmount_800c6714.addu(0x4L);
    if(copyrightFadeInAmount_800c6714.get() > 0x80L) {
      copyrightFadeInAmount_800c6714.setu(0x80L);
    }

    //LAB_800cabb8
    long sp40 = linkedListAddress_1f8003d8.get();

    //LAB_800cabcc
    for(int sp44 = 0; sp44 < 2; sp44++) {
      //LAB_800cabe8
      renderQuad(
        sp40,
        (_800bb114.get() | 0xeL) & 0xffffL,
        0x1028L,
        copyrightFadeInAmount_800c6714.get(),
        copyrightFadeInAmount_800c6714.get(),
        copyrightFadeInAmount_800c6714.get(),
        _800ce8f4.get(sp44 * 4).get(),
        _800ce8f4.get(sp44 * 4 + 1).get(),
        _800ce8f4.get(sp44 * 4 + 2).get(),
        _800ce8f4.get(sp44 * 4 + 3).get(),
        _800ce914.offset(4, sp44 * 4).get(),
        0x50L,
        _800ce8f4.get(sp44 * 4 + 2).get(),
        _800ce8f4.get(sp44 * 4 + 3).get(),
        0x64L,
        true
      );

      sp40 += 0x28L;
    }

    //LAB_800cadb0
    linkedListAddress_1f8003d8.setu(sp40);
  }

  @Method(0x800cadd0L)
  public static void renderMenuLogo() {
    logoFadeInAmount_800c66ec.addu(0x4L);
    if(logoFadeInAmount_800c66ec.get() > 0x80L) {
      logoFadeInAmount_800c66ec.setu(0x80L);
    }

    //LAB_800cae18
    //LAB_800cae2c
    for(int i = 0; i < 2; i++) {
      //LAB_800cae48
      renderQuad(
        linkedListAddress_1f8003d8.get(),
        _800bb116.get() | (int)((i << 0x6L) + 0x240L & 0x3c0L) >> 0x6L,
        0x68L,
        logoFadeInAmount_800c66ec.get(),
        logoFadeInAmount_800c66ec.get(),
        logoFadeInAmount_800c66ec.get(),
        0,
        0,
        _800ce91c.offset(i * 2).get(),
        0x58L,
        i * 255 - 184,
        -80,
        _800ce91c.offset(i * 2).get(),
        88,
        _1f8003c8.get() - 4,
        true
      );

      linkedListAddress_1f8003d8.addu(0x28L);
    }

    renderQuad(
      linkedListAddress_1f8003d8.get(),
      _800bb114.get() | 0xeL,
      0x1428L,
      logoFadeInAmount_800c66ec.get(),
      logoFadeInAmount_800c66ec.get(),
      logoFadeInAmount_800c66ec.get(),
      0,
      240,
      16,
      8,
      134,
      -14,
      16,
      8,
      _1f8003c8.get() - 4,
      true
    );

    linkedListAddress_1f8003d8.addu(0x28L);
  }

  @Method(0x800cb070L)
  public static void renderMenuBackground() {
    if(backgroundInitialized_800c6704.get() == 0) {
      backgroundScrollAmount_800c6708.set(-176);
      backgroundFadeInAmount_800c670c.setu(0);
      backgroundInitialized_800c6704.setu(0x1L);
    }

    //LAB_800cb0b0
    backgroundFadeInAmount_800c670c.addu(0x2L);
    if(backgroundFadeInAmount_800c670c.get() > 0x80L) {
      backgroundFadeInAmount_800c670c.setu(0x80L);
    }

    //LAB_800cb0ec
    //LAB_800cb100
    for(int i = 0; i < 6; i++) {
      //LAB_800cb11c
      final long page = _800bb120.offset((i / 3 & 1) * 2).get() | (int)_800ce920.offset(i % 3 * 4).get(0x3c0L) >> 6;

      renderQuad(
        linkedListAddress_1f8003d8.get(),
        page,
        40,
        backgroundFadeInAmount_800c670c.get(),
        backgroundFadeInAmount_800c670c.get(),
        backgroundFadeInAmount_800c670c.get(),
        0,
        0,
        128 - (i - i % 3 & 1),
        i / 3 * -88 + 255,
        i % 3 * 128 - 192,
        i / 3 * 255 - 120 + backgroundScrollAmount_800c6708.getSigned(),
        128,
        i / 3 * -88 + 255,
        _1f8003c8.get() - 3,
        false
      );

      linkedListAddress_1f8003d8.addu(0x28L);
    }

    //LAB_800cb370
    backgroundScrollAmount_800c6708.addu(1L);
    if(backgroundScrollAmount_800c6708.getSigned() > 0) {
      backgroundScrollAmount_800c6708.setu(0);
    }

    //LAB_800cb3b0
  }

  @Method(0x800cb4c4L)
  public static void renderQuad(final long address, final long page, final long clut, final long r, final long g, final long b, final long u, final long v, final long uw, final long uh, final long x, final long y, final long w, final long h, final long a14, final boolean transparency) {
    setCommand(0xcL, address, transparency, false);
    MEMORY.ref(1, address).offset(0x04L).setu(r);
    MEMORY.ref(1, address).offset(0x05L).setu(g);
    MEMORY.ref(1, address).offset(0x06L).setu(b);

    // Vertex 0
    MEMORY.ref(2, address).offset(0x08L).setu(x); // X
    MEMORY.ref(2, address).offset(0x0aL).setu(y); // Y
    MEMORY.ref(1, address).offset(0x0cL).setu(u); // U
    MEMORY.ref(1, address).offset(0x0dL).setu(v); // V
    MEMORY.ref(2, address).offset(0x0eL).setu(clut); // CLUT

    // Vertex 1
    MEMORY.ref(2, address).offset(0x10L).setu(x + w); // X
    MEMORY.ref(2, address).offset(0x12L).setu(y); // Y
    MEMORY.ref(1, address).offset(0x14L).setu(u + uw); // U
    MEMORY.ref(1, address).offset(0x15L).setu(v); // V
    MEMORY.ref(2, address).offset(0x16L).setu(page); // Page

    // Vertex 2
    MEMORY.ref(2, address).offset(0x18L).setu(x); // X
    MEMORY.ref(2, address).offset(0x1aL).setu(y + h); // Y
    MEMORY.ref(1, address).offset(0x1cL).setu(u); // U
    MEMORY.ref(1, address).offset(0x1dL).setu(v + uh); // V

    // Vertex 3
    MEMORY.ref(2, address).offset(0x20L).setu(x + w); // X
    MEMORY.ref(2, address).offset(0x22L).setu(y + h); // Y
    MEMORY.ref(1, address).offset(0x24L).setu(u + uw); // U
    MEMORY.ref(1, address).offset(0x25L).setu(v + uh); // V

    insertElementIntoLinkedList(tags_1f8003d0.deref().get((int)a14).getAddress(), address);
  }

  @Method(0x800cb5c4L)
  public static void FUN_800cb5c4() {
    //LAB_800cb5d8
    for(int i = 0; i < 3; i++) {
      //LAB_800cb5f4
      LoadImage(rectArray_800ce798.get(i), _800c6748.offset(i * 0x4L).get());
      DrawSync(0);
      removeFromLinkedList(_800c6748.offset(i * 0x4L).get());
    }

    //LAB_800cb688
  }

  @Method(0x800cb69cL)
  public static void deallocateFire() {
    deallocateTmdRenderer(_800c66d0.deref());

    //LAB_800cb6bc
    for(int i = 0; i < 4; i++) {
      //LAB_800cb6d8
      FUN_800ce448(_800c66d4.get(i).get());
    }

    //LAB_800cb714
  }

  @Method(0x800cb728L)
  public static void renderMenuLogoFire() {
    final SVECTOR sp58 = new SVECTOR().set(_800c68f0);
    final VECTOR sp60 = new VECTOR().set(_800c68f8);

    if(logoFireInitialized_800c6718.get() == 0) {
      logoFireInitialized_800c6718.setu(0x1L);
      _800c671c.setu(0);
    }

    //LAB_800cb7b4
    _800c671c.addu(0x2L);
    if(_800c671c.get() > 0xffL) {
      _800c671c.setu(0xffL);
    }

    //LAB_800cb7f0
    GsSetRefView2(GsRVIEW2_800c6760);

    UnboundedArrayRef<GsDOBJ2> dobj2s = _800c66d0.deref().dobj2s_00.deref();
    UnboundedArrayRef<GsCOORDINATE2> coord2s = _800c66d0.deref().coord2s_04.deref();

    //LAB_800cb834
    for(int i = 0; i < _800c66d0.deref().count_08.get(); i++) {
      final MATRIX sp10 = new MATRIX();
      final MATRIX sp30 = new MATRIX();

      //LAB_800cb85c
      FUN_800cc26c(sp58, coord2s.get(i));
      GsGetLws(dobj2s.get(i).coord2_04.deref(), sp10, sp30);
      GsSetLightMatrix(sp10);
      ScaleMatrixL(sp30, sp60);
      setRotTransMatrix(sp30);
      FUN_800cc388(dobj2s.get(i));
    }

    //LAB_800cb904
    //LAB_800cb908
    for(int i = 0; i < 0x4L; i++) {
      //LAB_800cb924
      animateFire(_800c66d4.get(i).get());
    }

    //LAB_800cb960
  }

  @Method(0x800cb974L)
  public static void renderLogoFlash() {
    final long sp24 = doubleBufferFrame_800bb108.get() ^ 1;

    if(logoFlashStage_800c66f0.get() == 0x2L) {
      return;
    }

    //LAB_800cb9b0
    if(logoFlashStage_800c66f0.get() == 0) {
      logoFlashStage_800c66f0.setu(0x1L);
      _800c66f4.setu(0);
      _800c66f8.setu(0);
      _800c66fc.setu(0);

      //LAB_800cb9ec
      for(int i = 0; i < 1; i++) {
        //LAB_800cba04
        _800c6700.offset(i * 2L).setu(0);
        _800c6702.offset(i * 2L).setu(0);
      }
    }

    //LAB_800cba54
    _800c66fc.addu(0x60L);
    if(_800c66fc.get() > 0x800L) {
      _800c66fc.setu(0x800L);
    }

    //LAB_800cba90
    final long colour = (int)(rsin(_800c66fc.get()) * 160) >> 12;

    // GP0.66 Textured quad, variable size, translucent, blended
    final long sp3c = linkedListAddress_1f8003d8.get();
    setCommand(0xeL, sp3c, true, false);
    MEMORY.ref(1, sp3c).offset(0x04L).setu(colour);
    MEMORY.ref(1, sp3c).offset(0x05L).setu(colour);
    MEMORY.ref(1, sp3c).offset(0x06L).setu(colour);
    MEMORY.ref(2, sp3c).offset(0x08L).setu(-192);
    MEMORY.ref(2, sp3c).offset(0x0aL).setu(-120);
    MEMORY.ref(1, sp3c).offset(0x0cL).setu(0);
    MEMORY.ref(1, sp3c).offset(0x0dL).setu(sp24 * 16);
    MEMORY.ref(2, sp3c).offset(0x10L).setu(256);
    MEMORY.ref(2, sp3c).offset(0x12L).setu(240);
    insertElementIntoLinkedList(tags_1f8003d0.deref().get(5).getAddress(), sp3c);
    linkedListAddress_1f8003d8.addu(0x14L);

    final long sp40 = linkedListAddress_1f8003d8.get();
    SetDrawMode(MEMORY.ref(4, sp40, DR_MODE::new), false, true, _800bb134.offset(doubleBufferFrame_800bb108.get() * 2).get(), null);
    insertElementIntoLinkedList(tags_1f8003d0.deref().get(5).getAddress(), sp40);
    linkedListAddress_1f8003d8.addu(0xcL);

    // GP0.66 Textured quad, variable size, translucent, blended
    final long sp44 = linkedListAddress_1f8003d8.get();
    setCommand(0xeL, sp44, true, false);
    MEMORY.ref(1, sp44).offset(0x04L).setu(colour); // R
    MEMORY.ref(1, sp44).offset(0x05L).setu(colour); // G
    MEMORY.ref(1, sp44).offset(0x06L).setu(colour); // B
    //                 1               0x07L       0x66L    // Command
    MEMORY.ref(2, sp44).offset(0x08L).setu(64);     // XX
    MEMORY.ref(2, sp44).offset(0x0aL).setu(-120);   // YY
    MEMORY.ref(1, sp44).offset(0x0cL).setu(0);      // U
    MEMORY.ref(1, sp44).offset(0x0dL).setu(sp24 * 16); // V
    //                 2               0x0eL                // CLUT
    MEMORY.ref(2, sp44).offset(0x10L).setu(128);    // W
    MEMORY.ref(2, sp44).offset(0x12L).setu(240);    // H
    insertElementIntoLinkedList(tags_1f8003d0.deref().get(5).getAddress(), sp44);
    linkedListAddress_1f8003d8.addu(0x14L);

    final long sp48 = linkedListAddress_1f8003d8.get();
    SetDrawMode(MEMORY.ref(4, sp48, DR_MODE::new), false, true, _800bb134.offset(doubleBufferFrame_800bb108.get() * 2).get() | 4, null);
    insertElementIntoLinkedList(tags_1f8003d0.deref().get(5).getAddress(), sp48);
    linkedListAddress_1f8003d8.addu(0xcL);

    if(_800c66fc.get() == 0x800L) {
      logoFlashStage_800c66f0.setu(0x2L);
    }

    //LAB_800cbe34
  }

  @Method(0x800cbe48L)
  public static TmdRenderingStruct parseTmdFile(final TmdWithId tmd) {
    final TmdRenderingStruct tmdRenderer = MEMORY.ref(4, addToLinkedListTail(0x10L), TmdRenderingStruct::new);
    tmdRenderer.count_08.set(prepareTmdRenderer(tmdRenderer, tmd));
    return tmdRenderer;
  }

  @Method(0x800cbeb4L)
  public static void deallocateTmdRenderer(final TmdRenderingStruct renderer) {
    removeFromLinkedList(renderer.coord2s_04.getPointer());
    removeFromLinkedList(renderer.dobj2s_00.getPointer());
    removeFromLinkedList(renderer.tmd_0c.getPointer());
    removeFromLinkedList(renderer.getAddress());
  }

  @Method(0x800cbf3cL)
  public static long prepareTmdRenderer(final TmdRenderingStruct tmdRenderer, final TmdWithId tmd) {
    adjustTmdPointers(tmd.tmd);

    tmdRenderer.dobj2s_00.set(MEMORY.ref(4, addToLinkedListTail(tmd.tmd.header.nobj.get() * 0x10L), UnboundedArrayRef.of(4, GsDOBJ2::new)));
    tmdRenderer.coord2s_04.set(MEMORY.ref(4, addToLinkedListTail(tmd.tmd.header.nobj.get() * 0x50L), UnboundedArrayRef.of(4, GsCOORDINATE2::new)));

    //LAB_800cc02c
    for(int objIndex = 0; objIndex < tmd.tmd.header.nobj.get(); objIndex++) {
      //LAB_800cc04c
      updateTmdPacketIlen(tmd.tmd.objTable, tmdRenderer.dobj2s_00.deref().get(objIndex), objIndex);
    }

    //LAB_800cc088
    //LAB_800cc09c
    return tmd.tmd.header.nobj.get();
  }

  @Method(0x800cc0b0L)
  public static void FUN_800cc0b0(final TmdRenderingStruct renderer, @Nullable final GsCOORDINATE2 superCoord2) {
    //LAB_800cc0f0
    for(int i = 0; i < renderer.count_08.get(); i++) {
      final GsCOORDINATE2 coord2 = renderer.coord2s_04.deref().get(i);
      final GsDOBJ2 dobj2 = renderer.dobj2s_00.deref().get(i);

      //LAB_800cc114
      GsInitCoordinate2(superCoord2, coord2);

      dobj2.coord2_04.set(coord2);
      coord2.coord.transfer.setX(100);
      coord2.coord.transfer.setY(-430);
      coord2.coord.transfer.setZ(-2048);
    }

    //LAB_800cc1a8
  }

  @Method(0x800cc1bcL)
  public static void setDobjAttributes(final TmdRenderingStruct renderer, final long dobjAttribute) {
    //LAB_800cc1e4
    for(int i = 0; i < renderer.count_08.get(); i++) {
      //LAB_800cc208
      renderer.dobj2s_00.deref().get(i).attribute_00.set(dobjAttribute);
    }

    //LAB_800cc25c
  }

  @Method(0x800cc26cL)
  public static void FUN_800cc26c(final SVECTOR a0, final GsCOORDINATE2 a1) {
    final MATRIX m = new MATRIX();
    m.set(identityMatrix_800c3568);
    m.transfer.set(a1.coord.transfer);
    RotMatrix_8003faf0(a0, m);
    a1.coord.set(m);
    a1.flg.set(0);
  }

  @Method(0x800cc388L)
  public static void FUN_800cc388(final GsDOBJ2 dobj2) {
    final long vertices = dobj2.tmd_08.deref().vert_top_00.get();
    final long normals = dobj2.tmd_08.deref().normal_top_08.get();
    long primitives = dobj2.tmd_08.deref().primitives_10.getPointer();
    long primitiveCount = dobj2.tmd_08.deref().n_primitive_14.get();
//    int primitiveIndex = 0;

    //LAB_800cc408
    while(primitiveCount != 0) {
//      final long primitive = primitives.get(primitiveIndex / 4).get();
      final long primitive = MEMORY.ref(4, primitives).get();
      final long command = primitive & 0xff04_0000L;
      final long len = primitive & 0xffffL;

      //LAB_800cc420
      primitiveCount -= len;

      //TODO pass in the array or something instead of the address to an element in the array?
      if(command == 0x3400_0000L) {
        //LAB_800cc528
        primitives = FUN_800cd72c(primitives, vertices, normals, len);
      } else if(command == 0x3700_0000L) {
        //LAB_800cc4a0
        primitives = FUN_800cc57c(primitives, vertices, len);
      } else if(command == 0x3c00_0000L) {
        //LAB_800cc4f8
        primitives = FUN_800cd2d8(primitives, vertices, normals, len);
      } else if(command == 0x3f00_0000L) {
        //LAB_800cc4cc
        primitives = FUN_800ccb78(primitives, vertices, len);
      }

      //LAB_800cc558
      //LAB_800cc560
    }

    //LAB_800cc568
  }

  @Method(0x800cc57cL)
  public static long FUN_800cc57c(final long primitives, final long vertices, final long len) {
    long sp18 = primitives;
    long sp20 = len;
    final long sp00 = _800c671c.get();
    long address = linkedListAddress_1f8003d8.get();

    //LAB_800cc5b0
    while(sp20 != 0) {
      //LAB_800cc5c8
      final long t5 = vertices + MEMORY.ref(2, sp18).offset(0x1cL).get() * 8;
      final long t6 = vertices + MEMORY.ref(2, sp18).offset(0x1eL).get() * 8;
      final long t7 = vertices + MEMORY.ref(2, sp18).offset(0x20L).get() * 8;
      CPU.MTC2(MEMORY.ref(4, t5).offset(0x0L).get(), 0L); // VXY0
      CPU.MTC2(MEMORY.ref(4, t5).offset(0x4L).get(), 1L); // VZ0
      CPU.MTC2(MEMORY.ref(4, t6).offset(0x0L).get(), 2L); // VXY1
      CPU.MTC2(MEMORY.ref(4, t6).offset(0x4L).get(), 3L); // VZ1
      CPU.MTC2(MEMORY.ref(4, t7).offset(0x0L).get(), 4L); // VXY2
      CPU.MTC2(MEMORY.ref(4, t7).offset(0x4L).get(), 5L); // VZ2
      CPU.COP2(0x280030L); // Perspective transformation triple

      MEMORY.ref(4, address).offset(0x0cL).setu(MEMORY.ref(4, sp18).offset(0x4L));
      MEMORY.ref(4, address).offset(0x18L).setu(MEMORY.ref(4, sp18).offset(0x8L));
      sp20--;

      if((int)CPU.CFC2(31L) >= 0) { // Were any flags set?
        //LAB_800cc674
        CPU.COP2(0x1400006L); // Normal clipping

        MEMORY.ref(4, address).offset(0x24L).setu(MEMORY.ref(4, sp18).offset(0xcL));

        if(CPU.MFC2(24L) != 0) { // MAC0
          //LAB_800cc6b0
          MEMORY.ref(1, address).offset(0x03L).setu(0x9L); // 9 words
          MEMORY.ref(4, address).offset(0x04L).setu(0x3680_8080L); // Shaded textured three-point polygon, semi-transparent, tex-blend
          MEMORY.ref(4, address).offset(0x08L).setu(CPU.MFC2(12L)); // SXY0
          MEMORY.ref(4, address).offset(0x14L).setu(CPU.MFC2(13L)); // SXY1
          MEMORY.ref(4, address).offset(0x20L).setu(CPU.MFC2(14L)); // SXY2

          if((int)CPU.CFC2(31L) >= 0) { // Flags
            //LAB_800cc6e8
            if(MEMORY.ref(2, address).offset(0x8L).getSigned() >= -0xc0L || MEMORY.ref(2, address).offset(0x14L).getSigned() >= -0xc0L || MEMORY.ref(2, address).offset(0x20L).getSigned() >= -0xc0L) {
              //LAB_800cc72c
              if(MEMORY.ref(2, address).offset(0xaL).getSigned() >= -0x78L || MEMORY.ref(2, address).offset(0x16L).getSigned() >= -0x78L || MEMORY.ref(2, address).offset(0x22L).getSigned() >= -0x78L) {
                //LAB_800cc770
                if(MEMORY.ref(2, address).offset(0x8L).getSigned() <= 0xc0L || MEMORY.ref(2, address).offset(0x14L).getSigned() <= 0xc0L || MEMORY.ref(2, address).offset(0x20L).getSigned() <= 0xc0L) {
                  //LAB_800cc7b4
                  if(MEMORY.ref(2, address).offset(0xaL).getSigned() <= 0x78L || MEMORY.ref(2, address).offset(0x16L).getSigned() <= 0x78L || MEMORY.ref(2, address).offset(0x22L).getSigned() <= 0x78L) {
                    //LAB_800cc7f8
                    CPU.COP2(0x158002dL); // Average of three Z values

                    MEMORY.ref(1, address).offset(0x04L).setu(MEMORY.ref(1, sp18).offset(0x10L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x05L).setu(MEMORY.ref(1, sp18).offset(0x11L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x06L).setu(MEMORY.ref(1, sp18).offset(0x12L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x10L).setu(MEMORY.ref(1, sp18).offset(0x14L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x11L).setu(MEMORY.ref(1, sp18).offset(0x15L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x12L).setu(MEMORY.ref(1, sp18).offset(0x16L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x1cL).setu(MEMORY.ref(1, sp18).offset(0x18L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x1dL).setu(MEMORY.ref(1, sp18).offset(0x19L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x1eL).setu(MEMORY.ref(1, sp18).offset(0x1aL).get() * sp00 / 0xffL);

                    // OTZ - Average Z value (for ordering table)
                    long a3 = (int)(CPU.MFC2(7L) + _800c6758.get()) >> _1f8003c4.get(0x1fL);
                    if(a3 > _1f8003cc.get()) {
                      a3 = _1f8003cc.get();
                    }

                    //LAB_800ccaf4
                    MEMORY.ref(4, address).setu(tags_1f8003d0.deref().get((int)a3).p.get() | 0x900_0000L);
                    tags_1f8003d0.deref().get((int)a3).num.set(0);
                    tags_1f8003d0.deref().get((int)a3).p.set(address & 0xff_ffffL);
                    address += 0x28L;
                  }
                }
              }
            }
          }
        }
      }

      //LAB_800ccb34
      sp18 += 0x24L;
    }

    //LAB_800ccb4c
    linkedListAddress_1f8003d8.setu(address);

    //LAB_800ccb68
    return sp18;
  }

  @Method(0x800ccb78L)
  public static long FUN_800ccb78(long primitives, long vertices, long len) {
    long sp18 = primitives;
    long sp20 = len;
    final long sp00 = _800c671c.get();
    final UnboundedArrayRef<GsOT_TAG> tags = orderingTables_8005a370.get((int)doubleBufferFrame_800bb108.get()).org_04.deref();
    long address = linkedListAddress_1f8003d8.get();

    //LAB_800ccbcc
    while(sp20 != 0) {
      //LAB_800ccbe4
      final SVECTOR t5 = MEMORY.ref(4, vertices + MEMORY.ref(2, sp18).offset(0x24L).get() * 8, SVECTOR::new);
      final SVECTOR t6 = MEMORY.ref(4, vertices + MEMORY.ref(2, sp18).offset(0x26L).get() * 8, SVECTOR::new);
      final SVECTOR t7 = MEMORY.ref(4, vertices + MEMORY.ref(2, sp18).offset(0x28L).get() * 8, SVECTOR::new);
      CPU.MTC2(t5.getXY(), 0); // VXY0
      CPU.MTC2(t5.getZ(),  1); // VZ0
      CPU.MTC2(t6.getXY(), 2); // VXY1
      CPU.MTC2(t6.getZ(),  3); // VY1
      CPU.MTC2(t7.getXY(), 4); // VXY2
      CPU.MTC2(t7.getZ(),  5); // VZ2
      CPU.COP2(0x28_0030L); // Perspective transform triple

      MEMORY.ref(4, address).offset(0x0cL).setu(MEMORY.ref(4, sp18).offset(0x4L));
      MEMORY.ref(4, address).offset(0x18L).setu(MEMORY.ref(4, sp18).offset(0x8L));
      sp20--;

      if((int)CPU.CFC2(31L) >= 0) { // Flags
        //LAB_800ccc90
        CPU.COP2(0x140_0006L); // Normal clipping

        MEMORY.ref(4, address).offset(0x24L).setu(MEMORY.ref(4, sp18).offset(0xcL));

        if(CPU.MFC2(24L) != 0) { // MAC0
          //LAB_800ccccc
          MEMORY.ref(1, address).offset(0x3L).setu(0xcL); // 12 words
          MEMORY.ref(4, address).offset(0x4L).setu(0x3e808080L); // Shaded textured four-point polygon, semi-transparent, tex-blend
          MEMORY.ref(4, address).offset(0x08L).setu(CPU.MFC2(12L)); // SXY0
          MEMORY.ref(4, address).offset(0x14L).setu(CPU.MFC2(13L)); // SXY1
          MEMORY.ref(4, address).offset(0x20L).setu(CPU.MFC2(14L)); // SXY2

          final long v0 = vertices + MEMORY.ref(2, sp18).offset(0x2aL).get() * 8;
          CPU.MTC2(MEMORY.ref(4, v0).offset(0x0L).get(), 0x0L); // VXY0
          CPU.MTC2(MEMORY.ref(4, v0).offset(0x4L).get(), 0x1L); // VZ0
          CPU.COP2(0x18_0001L); // Perspective transform single

          MEMORY.ref(4, address).offset(0x30L).setu(MEMORY.ref(4, sp18).offset(0x10L));

          if((int)CPU.CFC2(31L) >= 0) { // Flags
            //LAB_800ccd54
            MEMORY.ref(4, address).offset(0x2cL).setu(CPU.MFC2(14L)); // SXY2

            if(MEMORY.ref(2, address).offset(0x8L).getSigned() >= -0xc0L || MEMORY.ref(2, address).offset(0x14L).getSigned() >= -0xc0L || MEMORY.ref(2, address).offset(0x20L).getSigned() >= -0xc0L || MEMORY.ref(4, address).offset(0x2cL).getSigned() >= -0xc0L) {
              //LAB_800ccdb4
              if(MEMORY.ref(2, address).offset(0xaL).getSigned() >= -0x78L || MEMORY.ref(2, address).offset(0x16L).getSigned() >= -0x78L || MEMORY.ref(2, address).offset(0x22L).getSigned() >= -0x78L || MEMORY.ref(2, address).offset(0x2eL).getSigned() >= -0x78L) {
                //LAB_800cce0c
                if(MEMORY.ref(2, address).offset(0x8L).getSigned() < 0xc1L || MEMORY.ref(2, address).offset(0x14L).getSigned() < 0xc1L || MEMORY.ref(2, address).offset(0x20L).getSigned() < 0xc1L || MEMORY.ref(2, address).offset(0x2cL).getSigned() < 0xc1L) {
                  //LAB_800cce64
                  if(MEMORY.ref(2, address).offset(0xaL).getSigned() < 0x79L || MEMORY.ref(2, address).offset(0x16L).getSigned() < 0x79L || MEMORY.ref(2, address).offset(0x22L).getSigned() < 0x79L || MEMORY.ref(2, address).offset(0x2eL).getSigned() < 0x79L) {
                    //LAB_800ccebc
                    MEMORY.ref(1, address).offset(0x04L).setu(MEMORY.ref(1, sp18).offset(0x14L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x05L).setu(MEMORY.ref(1, sp18).offset(0x15L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x06L).setu(MEMORY.ref(1, sp18).offset(0x16L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x10L).setu(MEMORY.ref(1, sp18).offset(0x18L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x11L).setu(MEMORY.ref(1, sp18).offset(0x19L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x12L).setu(MEMORY.ref(1, sp18).offset(0x1aL).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x1cL).setu(MEMORY.ref(1, sp18).offset(0x1cL).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x1dL).setu(MEMORY.ref(1, sp18).offset(0x1dL).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x1eL).setu(MEMORY.ref(1, sp18).offset(0x1eL).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x28L).setu(MEMORY.ref(1, sp18).offset(0x20L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x29L).setu(MEMORY.ref(1, sp18).offset(0x21L).get() * sp00 / 0xffL);
                    MEMORY.ref(1, address).offset(0x2aL).setu(MEMORY.ref(1, sp18).offset(0x22L).get() * sp00 / 0xffL);

                    MEMORY.ref(4, address).setu(0xc00_0000L | tags.get((int)_800c6758.get()).get() & 0xff_ffffL);
                    tags.get((int)_800c6758.get()).num.set(0x0c);
                    tags.get((int)_800c6758.get()).p.set(address & 0xff_ffffL);
                    address += 0x34L;
                  }
                }
              }
            }
          }
        }
      }

      //LAB_800cd294
      sp18 += 0x2cL;
    }

    //LAB-800cd2ac
    linkedListAddress_1f8003d8.setu(address);

    //LAB_800cd2c8
    return sp18;
  }

  @Method(0x800cd2d8L)
  public static long FUN_800cd2d8(final long a0, final long a1, final long a2, final long a3) {
    assert false;
    return 0;
  }

  @Method(0x800cd72cL)
  public static long FUN_800cd72c(final long a0, final long a1, final long a2, final long a3) {
    assert false;
    return 0;
  }

  @Method(0x800cdaa0L)
  public static long FUN_800cdaa0(final RECT rect, final long widthSomething, final long a2, final long a3) {
    final long addr0 = addToLinkedListTail(0x20L);
    final long addr1 = addToLinkedListTail(rect.w.get() / (2 - widthSomething) * rect.h.get());
    final long addr2 = addToLinkedListTail(rect.w.get() / (2 - widthSomething) * rect.h.get());

    MEMORY.ref(2, addr0).offset(0x00L).setu(rect.x.get());
    MEMORY.ref(2, addr0).offset(0x02L).setu(rect.y.get());
    MEMORY.ref(2, addr0).offset(0x04L).setu(rect.w.get() / (4 - widthSomething * 2));
    MEMORY.ref(2, addr0).offset(0x06L).setu(rect.h.get());
    MEMORY.ref(4, addr0).offset(0x08L).setu(addr1);
    MEMORY.ref(4, addr0).offset(0x0cL).setu(addr2);
    MEMORY.ref(4, addr0).offset(0x10L).setu(a2);
    MEMORY.ref(4, addr0).offset(0x14L).setu(widthSomething);
    MEMORY.ref(2, addr0).offset(0x18L).setu(a3);
    MEMORY.ref(2, addr0).offset(0x1aL).setu(a2 / 2 * 2);
    MEMORY.ref(2, addr0).offset(0x1cL).setu(a2 / 2 * 2);
    return addr0;
  }

  @Method(0x800cdcb0L)
  public static void animateFire(final long a0) {
    final long a1;
    final long v1;
    final RECT sp10 = new RECT();
    final RECT sp18 = new RECT();
    final RECT sp20 = new RECT();
    final RECT sp28 = new RECT();

    if(MEMORY.ref(2, a0).offset(0x18L).get() == 0) {
      return;
    }

    //LAB_800cdce0
    MEMORY.ref(2, a0).offset(0x1cL).addu(0x1L);
    if(MEMORY.ref(2, a0).offset(0x1cL).get() < MEMORY.ref(2, a0).offset(0x1aL).get()) {
      return;
    }

    //LAB_800cdd28
    MEMORY.ref(2, a0).offset(0x1cL).setu(0);
    if(MEMORY.ref(4, a0).offset(0x10L).get(0x1L) == 0) {
      v1 = MEMORY.ref(2, a0).offset(0x18L).get();
      a1 = MEMORY.ref(2, a0).offset(0x4L).get();
      MEMORY.ref(2, a0).offset(0x18L).setu(v1 % a1);
      if(MEMORY.ref(2, a0).offset(0x18L).getSigned() > 0) {
        sp10.x.set((short)(MEMORY.ref(2, a0).get() + MEMORY.ref(2, a0).offset(0x4L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp10.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp10.w.set((short)MEMORY.ref(2, a0).offset(0x18L).get());
        sp10.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
        sp18.x.set((short)MEMORY.ref(2, a0).get());
        sp18.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp18.w.set((short)(MEMORY.ref(2, a0).offset(0x4L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp18.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
        sp20.x.set((short)MEMORY.ref(2, a0).get());
        sp20.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp20.w.set((short)MEMORY.ref(2, a0).offset(0x18L).get());
        sp20.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
        sp28.x.set((short)(MEMORY.ref(2, a0).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp28.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp28.w.set((short)(MEMORY.ref(2, a0).offset(0x4L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp28.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
      } else {
        //LAB_800cdf14
        sp10.x.set((short)MEMORY.ref(2, a0).get());
        sp10.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp10.w.set((short)-MEMORY.ref(2, a0).offset(0x18L).get());
        sp10.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
        sp18.x.set((short)(MEMORY.ref(2, a0).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp18.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp18.w.set((short)(MEMORY.ref(2, a0).offset(0x4L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp18.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
        sp20.x.set((short)(MEMORY.ref(2, a0).get() + MEMORY.ref(2, a0).offset(0x4L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp20.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp20.w.set((short)-MEMORY.ref(2, a0).offset(0x18L).get());
        sp20.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
        sp28.x.set((short)MEMORY.ref(2, a0).get());
        sp28.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp28.w.set((short)(MEMORY.ref(2, a0).offset(0x4L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp28.h.set((short)MEMORY.ref(2, a0).offset(0x6L).get());
      }

      //LAB_800ce090
    } else {
      //LAB_800ce098
      v1 = MEMORY.ref(2, a0).offset(0x18L).get();
      a1 = MEMORY.ref(2, a0).offset(0x6L).get();
      MEMORY.ref(2, a0).offset(0x18L).setu(v1 % a1);
      if((int)MEMORY.ref(2, a0).offset(0x18L).get() > 0) {
        sp10.x.set((short)MEMORY.ref(2, a0).get());
        sp10.y.set((short)(MEMORY.ref(2, a0).offset(0x2L).get() + MEMORY.ref(2, a0).offset(0x6L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp10.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp10.h.set((short)MEMORY.ref(2, a0).offset(0x18L).get());
        sp18.x.set((short)MEMORY.ref(2, a0).get());
        sp18.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp18.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp18.h.set((short)(MEMORY.ref(2, a0).offset(0x6L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp20.x.set((short)MEMORY.ref(2, a0).get());
        sp20.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp20.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp20.h.set((short)MEMORY.ref(2, a0).offset(0x18L).get());
        sp28.x.set((short)MEMORY.ref(2, a0).get());
        sp28.y.set((short)(MEMORY.ref(2, a0).offset(0x2L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp28.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp28.h.set((short)(MEMORY.ref(2, a0).offset(0x6L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
      } else {
        //LAB_800ce25c
        sp10.x.set((short)MEMORY.ref(2, a0).get());
        sp10.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp10.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp10.h.set((short)-MEMORY.ref(2, a0).offset(0x18L).get());
        sp18.x.set((short)MEMORY.ref(2, a0).get());
        sp18.y.set((short)(MEMORY.ref(2, a0).offset(0x2L).get() - MEMORY.ref(2, a0).offset(0x18L).get()));
        sp18.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp18.h.set((short)(MEMORY.ref(2, a0).offset(0x6L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp20.x.set((short)MEMORY.ref(2, a0).get());
        sp20.y.set((short)(MEMORY.ref(2, a0).offset(0x2L).get() + MEMORY.ref(2, a0).offset(0x6L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
        sp20.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp20.h.set((short)-MEMORY.ref(2, a0).offset(0x18L).get());
        sp28.x.set((short)MEMORY.ref(2, a0).get());
        sp28.y.set((short)MEMORY.ref(2, a0).offset(0x2L).get());
        sp28.w.set((short)MEMORY.ref(2, a0).offset(0x4L).get());
        sp28.h.set((short)(MEMORY.ref(2, a0).offset(0x6L).get() + MEMORY.ref(2, a0).offset(0x18L).get()));
      }
    }

    //LAB_800ce3d8
    StoreImage(sp10, MEMORY.ref(4, a0).offset(0xcL).get());
    StoreImage(sp18, MEMORY.ref(4, a0).offset(0x8L).get());
    LoadImage(sp20, MEMORY.ref(4, a0).offset(0xcL).get());
    LoadImage(sp28, MEMORY.ref(4, a0).offset(0x8L).get());

    //LAB_800ce434
  }

  @Method(0x800ce448L)
  public static void FUN_800ce448(final long a0) {
    removeFromLinkedList(MEMORY.ref(4, a0).offset(0xcL).get());
    removeFromLinkedList(MEMORY.ref(4, a0).offset(0x8L).get());
    removeFromLinkedList(a0);
  }
}
