
DROP TABLE IF EXISTS NF_Channel;

CREATE TABLE NF_Channel (
  channelId BIGSERIAL PRIMARY KEY,
  channelName VARCHAR(50) NOT NULL
);