package blog.db.schema;

import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Table;

public class SimpleBlogDatabaseModel extends DatabaseModel
{{
  packageName("blog.db");
  database("Database");
  enableTableModelClassesGeneration();
  
  Table postTable = table("Post")
    .column("id", DataType.INTEGER)
      .identifierColumn()
    .column("title", DataType.VARCHAR)
    .column("content", DataType.VARCHAR)
  .build();
  
  Table tagTable = table("Tag")
    .column("name", DataType.VARCHAR)
      .identifierColumn()
  .build();
  
  table("Post_Tag")
    .column("posts_id", DataType.INTEGER)
      .references(postTable)
        .remote("hasTags")
    .column("tags_name", DataType.VARCHAR)
      .references(tagTable)
        .remote("tagedPosts")
  .build();
  
}}
