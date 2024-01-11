
CREATE TABLE post(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title TEXT NOT NULL,
  md_content TEXT NOT NULL
);

CREATE TABLE tag(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name TEXT NOT NULL,
  UNIQUE(name)
);

CREATE TABLE post_tag(
  post_id UUID NOT NULL REFERENCES post(id),
  tag_id UUID NOT NULL REFERENCES tag(id)
);
