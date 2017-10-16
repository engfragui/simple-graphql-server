package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.service.http.DataSourceManager;
import graphql.schema.Coercing;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import graphql.servlet.GraphQLQueryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Collection;
import java.util.function.UnaryOperator;

public class SchemaProvider implements GraphQLQueryProvider {
    private static final Logger LOG = LoggerFactory.getLogger(SchemaProvider.class);

    private static final String BOOK = "Book";
    private static final String AUTHOR = "Author";
    private static final String REVIEW = "Review";

    private final BookDetailFetcher bookDetailFetcher;
    private final AuthorFetcher authorFetcher;
    private final ReviewFetcher reviewFetcher;

    public SchemaProvider(DataSourceManager dataSourceManager) {
        bookDetailFetcher = new BookDetailFetcher(dataSourceManager);
        authorFetcher = new AuthorFetcher(dataSourceManager);
        reviewFetcher = new ReviewFetcher(dataSourceManager);
    }

    public InputStream getSchema() {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("book.graphqls");
    }

    public GraphQLSchema makeExecutableSchema() throws IOException {
        final InputStream schemaFileInputStream = getSchema();
        return makeExecutableSchema(schemaFileInputStream);
    }

    public GraphQLSchema makeExecutableSchema(InputStream schemaFileInputStream) {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(new InputStreamReader(schemaFileInputStream));

        return schemaGenerator.makeExecutableSchema(typeRegistry, buildRuntimeWiring());
    }

    private RuntimeWiring buildRuntimeWiring() {
        RuntimeWiring.Builder wiring = RuntimeWiring.newRuntimeWiring();

        wiring.scalar(new GraphQLScalarType("Date", "A date value", new Coercing<Instant, Instant>() {
            @Override
            public Instant serialize(Object o) {
                if (!(o instanceof Instant)) {
                    throw new IllegalArgumentException("I thought you were gonna pass me an Instant, but I got a " + o.getClass().getName());
                }
                return (Instant) o;
            }

            @Override
            public Instant parseValue(Object o) {
                if (!(o instanceof Instant)) {
                    throw new IllegalArgumentException("I thought you were gonna pass me an Instant, but I got a " + o.getClass().getName());
                }
                return (Instant) o;
            }

            @Override
            public Instant parseLiteral(Object o) {
                if (!(o instanceof String)) {
                    throw new IllegalArgumentException("I thought you were gonna pass me a String, but I got a " + o.getClass().getName());
                }
                return Instant.parse((String) o);
            }
        }));

        wiring.type("Query", new BookDataFetcherBind("book"));

        wiring.type(BOOK, new BookDataFetcherBind("id"));
        wiring.type(BOOK, new BookDataFetcherBind("isbn"));
        wiring.type(BOOK, new BookDataFetcherBind("title"));
        wiring.type(BOOK, new AuthorDataFetcherBind("author"));
        wiring.type(BOOK, new ReviewDataFetcherBind("review"));

        wiring.type(AUTHOR, new AuthorDataFetcherBind("id"));
        wiring.type(AUTHOR, new AuthorDataFetcherBind("name"));

        wiring.type(REVIEW, new ReviewDataFetcherBind("id"));
        wiring.type(REVIEW, new ReviewDataFetcherBind("title"));
        wiring.type(REVIEW, new ReviewDataFetcherBind("content"));

        return wiring.build();
    }

    @Override
    public Collection<GraphQLFieldDefinition> getQueries() {
        try {
            LOG.info("getQuery");
            return makeExecutableSchema().getQueryType().getFieldDefinitions();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("I have a problem: " + e);
        }
        return null;
    }

    class BookDataFetcherBind implements UnaryOperator<TypeRuntimeWiring.Builder> {
        private final String fieldName;

        BookDataFetcherBind(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
            return builder.dataFetcher(fieldName, bookDetailFetcher);
        }
    }

    class AuthorDataFetcherBind implements UnaryOperator<TypeRuntimeWiring.Builder> {
        private final String fieldName;

        AuthorDataFetcherBind(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
            return builder.dataFetcher(fieldName, authorFetcher);
        }
    }

    class ReviewDataFetcherBind implements UnaryOperator<TypeRuntimeWiring.Builder> {
        private final String fieldName;

        ReviewDataFetcherBind(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
            return builder.dataFetcher(fieldName, reviewFetcher);
        }
    }
}
