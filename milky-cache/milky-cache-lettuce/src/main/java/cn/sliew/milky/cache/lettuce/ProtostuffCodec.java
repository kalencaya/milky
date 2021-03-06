package cn.sliew.milky.cache.lettuce;

import cn.sliew.milky.log.Logger;
import cn.sliew.milky.log.LoggerFactory;
import cn.sliew.milky.serialize.protostuff.ProtostuffDataInputView;
import cn.sliew.milky.serialize.protostuff.ProtostuffDataOutputView;
import io.lettuce.core.codec.RedisCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ProtostuffCodec<K, V> implements RedisCodec<K, V> {

    private Logger log = LoggerFactory.getLogger(ProtostuffCodec.class);

    public static final ProtostuffCodec INSTANCE = new ProtostuffCodec();

    @Override
    public K decodeKey(ByteBuffer byteBuffer) {
        return (K) decode(byteBuffer);
    }

    @Override
    public V decodeValue(ByteBuffer byteBuffer) {
        return (V) decode(byteBuffer);
    }

    @Override
    public ByteBuffer encodeKey(K k) {
        return encode(k);
    }

    @Override
    public ByteBuffer encodeValue(V v) {
        return encode(v);
    }

    private ByteBuffer encode(Object obj) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ProtostuffDataOutputView outputView = new ProtostuffDataOutputView(outputStream);
            outputView.writeObject(obj);
            outputView.flushBuffer();
            return ByteBuffer.wrap(outputStream.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Object decode(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return null;
        }
        try {
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ProtostuffDataInputView inputView = new ProtostuffDataInputView(inputStream);
            return inputView.readObject();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
