package legend.game.types;

import legend.core.memory.Value;
import legend.core.memory.types.ArrayRef;
import legend.core.memory.types.ByteRef;
import legend.core.memory.types.ConsumerRef;
import legend.core.memory.types.IntRef;
import legend.core.memory.types.MemoryRef;
import legend.core.memory.types.Pointer;
import legend.core.memory.types.ShortRef;

public class JoyData implements MemoryRef {
  private final Value ref;

  public final IntRef int00;
  public final IntRef int04;
  public final IntRef int08;
  public final Pointer<ArrayRef<JoyData>> joyDataPtr0c;
  public final Pointer<JoyData> joyDataPtr10;
  public final Pointer<ConsumerRef<JoyData>> func14;
  public final Pointer<ConsumerRef<JoyData>> func18;
  // Unknown
  public final ArrayRef<ByteRef> byte24;
  public final Pointer<ArrayRef<ByteRef>> bytePtr28;
  public final Pointer<ArrayRef<ByteRef>> bytePtr2c;
  public final Pointer<ArrayRef<ByteRef>> bytePtr30;
  public final ByteRef byte34;
  public final ByteRef byte35;
  public final ByteRef byte36;
  public final ByteRef byte37;
  public final ByteRef byte38;
  public final ByteRef byte39;
  // Unknown
  public final Pointer<ArrayRef<ByteRef>> inputBufferPtr_bytePtr3c;
  public final Pointer<ArrayRef<ByteRef>> bytePtr40;
  public final ByteRef inputBufferIndex_byte44;
  public final ByteRef byte45;
  public final ByteRef byte46;
  public final ByteRef byte47;
  // Unknown
  public final ByteRef byte49;
  public final ByteRef byte4a;
  // Unknown
  public final IntRef counter_int4c;
  public final ByteRef byte50;
  public final ByteRef byte51;
  public final ByteRef byte52;
  public final ByteRef byte53;
  // Unknown
  public final ArrayRef<ByteRef> byteArr57;
  public final ArrayRef<ByteRef> byteArr5d;
  public final ByteRef bytee3;
  public final ByteRef bytee4;
  // Unknown
  public final ShortRef shorte6;
  public final ByteRef bytee8;
  public final ByteRef bytee9;
  public final ByteRef byteea;

  public JoyData(final Value ref) {
    this.ref = ref;

    this.int00 = ref.offset(4, 0x00L).cast(IntRef::new);
    this.int04 = ref.offset(4, 0x04L).cast(IntRef::new);
    this.int08 = ref.offset(4, 0x08L).cast(IntRef::new);
    this.joyDataPtr0c = ref.offset(4, 0x0cL).cast(Pointer.deferred(ArrayRef.of(JoyData.class, 4, 240, JoyData::new)));
    this.joyDataPtr10 = ref.offset(4, 0x10L).cast(Pointer.of(JoyData::new));
    this.func14 = ref.offset(4, 0x14L).cast(Pointer.of(ConsumerRef::new));
    this.func18 = ref.offset(4, 0x18L).cast(Pointer.of(ConsumerRef::new));

    this.byte24 = ref.offset(1, 0x24L).cast(ArrayRef.of(ByteRef.class, 4, 1, ByteRef::new));
    this.bytePtr28 = ref.offset(4, 0x28L).cast(Pointer.deferred(ArrayRef.of(ByteRef.class, 4, 1, ByteRef::new)));
    this.bytePtr2c = ref.offset(4, 0x2cL).cast(Pointer.deferred(ArrayRef.of(ByteRef.class, 4, 1, ByteRef::new)));
    this.bytePtr30 = ref.offset(4, 0x30L).cast(Pointer.deferred(ArrayRef.of(ByteRef.class, 2, 1, ByteRef::new)));
    this.byte34 = ref.offset(1, 0x34L).cast(ByteRef::new);
    this.byte35 = ref.offset(1, 0x35L).cast(ByteRef::new);
    this.byte36 = ref.offset(1, 0x36L).cast(ByteRef::new);
    this.byte37 = ref.offset(1, 0x37L).cast(ByteRef::new);
    this.byte38 = ref.offset(1, 0x38L).cast(ByteRef::new);
    this.byte39 = ref.offset(1, 0x39L).cast(ByteRef::new);

    this.inputBufferPtr_bytePtr3c = ref.offset(4, 0x3cL).cast(Pointer.deferred(ArrayRef.of(ByteRef.class, 35, 1, ByteRef::new)));
    this.bytePtr40 = ref.offset(4, 0x40L).cast(Pointer.deferred(ArrayRef.of(ByteRef.class, 37, 1, ByteRef::new)));
    this.inputBufferIndex_byte44 = ref.offset(1, 0x44L).cast(ByteRef::new);
    this.byte45 = ref.offset(1, 0x45L).cast(ByteRef::new);
    this.byte46 = ref.offset(1, 0x46L).cast(ByteRef::new);
    this.byte47 = ref.offset(1, 0x47L).cast(ByteRef::new);

    this.byte49 = ref.offset(1, 0x49L).cast(ByteRef::new);
    this.byte4a = ref.offset(1, 0x4aL).cast(ByteRef::new);

    this.counter_int4c = ref.offset(4, 0x4cL).cast(IntRef::new);
    this.byte50 = ref.offset(1, 0x50L).cast(ByteRef::new);
    this.byte51 = ref.offset(1, 0x51L).cast(ByteRef::new);
    this.byte52 = ref.offset(1, 0x52L).cast(ByteRef::new);
    this.byte53 = ref.offset(1, 0x53L).cast(ByteRef::new);

    this.byteArr57 = ref.offset(6, 0x57L).cast(ArrayRef.of(ByteRef.class, 6, 1, ByteRef::new));
    this.byteArr5d = ref.offset(6, 0x5dL).cast(ArrayRef.of(ByteRef.class, 6, 1, ByteRef::new));
    this.bytee3 = ref.offset(1, 0xe3L).cast(ByteRef::new);
    this.bytee4 = ref.offset(1, 0xe4L).cast(ByteRef::new);

    this.shorte6 = ref.offset(2, 0xe6L).cast(ShortRef::new);
    this.bytee8 = ref.offset(1, 0xe8L).cast(ByteRef::new);
    this.bytee9 = ref.offset(1, 0xe9L).cast(ByteRef::new);
    this.byteea = ref.offset(1, 0xeaL).cast(ByteRef::new);
  }

  @Override
  public long getAddress() {
    return this.ref.getAddress();
  }
}