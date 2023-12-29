package com.sellcon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSelling_Product is a Querydsl query type for Selling_Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelling_Product extends EntityPathBase<Selling_Product> {

    private static final long serialVersionUID = 73611873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSelling_Product selling_Product = new QSelling_Product("selling_Product");

    public final NumberPath<Long> barcode = createNumber("barcode", Long.class);

    public final StringPath barcode_image = createString("barcode_image");

    public final StringPath checkp = createString("checkp");

    public final QMember member;

    public final QProduct product;

    public final DateTimePath<java.util.Date> regdate = createDateTime("regdate", java.util.Date.class);

    public final NumberPath<Integer> sellingprice = createNumber("sellingprice", Integer.class);

    public final NumberPath<Long> sseq = createNumber("sseq", Long.class);

    public final DateTimePath<java.util.Date> valid = createDateTime("valid", java.util.Date.class);

    public QSelling_Product(String variable) {
        this(Selling_Product.class, forVariable(variable), INITS);
    }

    public QSelling_Product(Path<? extends Selling_Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSelling_Product(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSelling_Product(PathMetadata metadata, PathInits inits) {
        this(Selling_Product.class, metadata, inits);
    }

    public QSelling_Product(Class<? extends Selling_Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

