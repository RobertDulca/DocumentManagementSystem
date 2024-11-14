package at.fhtw.swkom.paperless.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Document
 */

@JsonTypeName("document")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-17T08:44:06.510922473Z[Etc/UTC]", comments = "Generator version: 7.10.0-SNAPSHOT")
public class DocumentDTO {

    private Integer id;

    private String title;

    private String content;

    private String created;

    public DocumentDTO() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public DocumentDTO(String title) {
        this.title = title;
    }

    public DocumentDTO id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * The id of the document
     *
     * @return id
     */

    @Schema(name = "id", description = "The id of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DocumentDTO title(String title) {
        this.title = title;
        return this;
    }

    /**
     * The title of the document
     *
     * @return title
     */
    @NotNull
    @Schema(name = "title", description = "The title of the document", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DocumentDTO author(String author) {
        this.content = author;
        return this;
    }

    /**
     * The author of the document's contents
     *
     * @return author
     */

    @Schema(name = "author", description = "The author of the document's contents", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("author")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DocumentDTO created(String created) {
        this.created = created;
        return this;
    }

    /**
     * Get created
     *
     * @return created
     */

    @Schema(name = "created", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentDTO document = (DocumentDTO) o;
        return Objects.equals(this.id, document.id) &&
                Objects.equals(this.title, document.title) &&
                Objects.equals(this.content, document.content) &&
                Objects.equals(this.created, document.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, created);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Document {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    author: ").append(toIndentedString(content)).append("\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

