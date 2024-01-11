
INSERT INTO post(id, title, md_content) VALUES
    ('77a15137-8ff3-4207-8a0b-fd52efd5d417', 'Example post 1', '# Example post 1'||E'\n'||'Some content'),
    ('4f072df6-9706-419b-9dcf-84670660b5f4', 'Example post 2', '# Example post 2'||E'\n'||'Some content'),
    ('94d3fa0b-db14-4473-9147-023d590ad8eb', 'Example post 3', '# Example post 3'||E'\n'||'Some content'),
    ('fa08c933-eb4b-4691-b339-e385f4f9fec1', 'Example post 4', '# Example post 4'||E'\n'||'Some content'),
    ('1922fb1b-6464-4ba8-9b0f-e8aa0f5e01c2', 'Example post 5', '# Example post 5'||E'\n'||'Some content'),
    ('d76fc6d0-ee9e-44d0-8b3f-3542a57e0a4f', 'Example post 6', '# Example post 6'||E'\n'||'Some content'),
    ('f567ec5a-aa2a-4a46-8fbe-148a45c69214', 'Example post 7', '# Example post 7'||E'\n'||'Some content'),
    ('08ea45c2-fbee-4a1d-97dc-b6f1f9381b58', 'Example post 8', '# Example post 8'||E'\n'||'Some content'),
    ('ff7fd55e-3188-439e-ab12-46d162072738', 'Example post 9', '# Example post 9'||E'\n'||'Some content'),
    ('6aaf20a3-c5ac-4851-b6e1-3ebe4ce40c0d', 'Example post 10', '# Example post 10'||E'\n'||'Some content'),
    ('b05c0c4b-527e-4869-8b21-722a7436277e', 'Example post 11', '# Example post 11'||E'\n'||'Some content')
;

INSERT INTO tag(id, name) VALUES
    ('b0f51a45-74fb-4605-8f3e-09c44c2a8553', 'tag1'),
    ('e4663873-7bdc-4f17-adbe-46fa2675f69a', 'tag2'),
    ('5fc9670f-92e9-47b2-8898-2984e2dd3f7a', 'tag3'),
    ('c677ed76-3ceb-4fe5-8e08-25a6beae3e73', 'tag4'),
    ('1844fd43-e8ba-4225-ba07-26cb9f6cac9b', 'tag5')
;

INSERT INTO post_tag(post_id, tag_id) VALUES
    ('77a15137-8ff3-4207-8a0b-fd52efd5d417', 'b0f51a45-74fb-4605-8f3e-09c44c2a8553'),
    ('4f072df6-9706-419b-9dcf-84670660b5f4', 'b0f51a45-74fb-4605-8f3e-09c44c2a8553'),
    ('94d3fa0b-db14-4473-9147-023d590ad8eb', 'e4663873-7bdc-4f17-adbe-46fa2675f69a'),
    ('fa08c933-eb4b-4691-b339-e385f4f9fec1', 'e4663873-7bdc-4f17-adbe-46fa2675f69a'),
    ('1922fb1b-6464-4ba8-9b0f-e8aa0f5e01c2', '5fc9670f-92e9-47b2-8898-2984e2dd3f7a'),
    ('d76fc6d0-ee9e-44d0-8b3f-3542a57e0a4f', '5fc9670f-92e9-47b2-8898-2984e2dd3f7a'),
    ('f567ec5a-aa2a-4a46-8fbe-148a45c69214', 'c677ed76-3ceb-4fe5-8e08-25a6beae3e73'),
    ('08ea45c2-fbee-4a1d-97dc-b6f1f9381b58', 'c677ed76-3ceb-4fe5-8e08-25a6beae3e73'),
    ('ff7fd55e-3188-439e-ab12-46d162072738', '1844fd43-e8ba-4225-ba07-26cb9f6cac9b'),
    ('6aaf20a3-c5ac-4851-b6e1-3ebe4ce40c0d', '1844fd43-e8ba-4225-ba07-26cb9f6cac9b'),
    ('b05c0c4b-527e-4869-8b21-722a7436277e', '1844fd43-e8ba-4225-ba07-26cb9f6cac9b')
;
