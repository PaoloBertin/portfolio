DROP TABLE IF EXISTS tools;
DROP TABLE IF EXISTS identifiers;
DROP TABLE IF EXISTS watchlists;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS portfolio_line_item;
DROP TABLE IF EXISTS line_items;
DROP TABLE IF EXISTS portfolios;

CREATE TABLE IF NOT EXISTS identifiers (
    isin VARCHAR(13),
    watchlist_id BIGINT,

    PRIMARY KEY(isin)
);

CREATE TABLE IF NOT EXISTS watchlists(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS tools(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(25),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS products(
    id BIGINT NOT NULL AUTO_INCREMENT,
    isin VARCHAR(13) UNIQUE,
    name VARCHAR(50),
    tool_id BIGINT NOT NULL,

    PRIMARY KEY(id),

    CONSTRAINT products_fk_01 FOREIGN KEY (tool_id) REFERENCES tools(id)
);

CREATE TABLE IF NOT EXISTS line_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    isin VARCHAR(13),
    quantity INTEGER,
    loading_price DECIMAL(12, 4),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS portfolios(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    cash DECIMAL(12,4),

    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS portfolio_line_item(
    portfolio_id BIGINT,
    line_item_id BIGINT,

    CONSTRAINT portfolio_line_item_uk_01 UNIQUE (line_item_id),
    CONSTRAINT portfolio_line_item_fk_01 FOREIGN KEY (portfolio_id) REFERENCES portfolios(id),
    CONSTRAINT portfolio_line_item_fk_02 FOREIGN KEY (line_item_id) REFERENCES line_items(id)
);
