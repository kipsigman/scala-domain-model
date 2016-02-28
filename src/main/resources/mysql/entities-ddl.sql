DROP TABLE IF EXISTS content_image;
DROP TABLE IF EXISTS image;

CREATE TABLE image (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    mime_type VARCHAR(255) NOT NULL,
    width SMALLINT UNSIGNED NOT NULL,
    height SMALLINT UNSIGNED NOT NULL,
    caption VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE content_image (
    content_class VARCHAR(255) NOT NULL,
    content_id INT UNSIGNED NOT NULL,
    image_id INT UNSIGNED NOT NULL,
    display_type VARCHAR(255) NOT NULL,
    display_position SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY(content_class, content_id, image_id),
	CONSTRAINT fk_ai_image_id FOREIGN KEY(image_id) REFERENCES image(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;