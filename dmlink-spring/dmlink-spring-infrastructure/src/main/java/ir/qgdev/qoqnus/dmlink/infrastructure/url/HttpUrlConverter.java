package ir.qgdev.qoqnus.dmlink.infrastructure.url;

import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import jakarta.persistence.AttributeConverter;

@jakarta.persistence.Converter(autoApply = true)
public class HttpUrlConverter implements AttributeConverter<HttpUrl, String> {
    @Override
    public String convertToDatabaseColumn(HttpUrl attribute) {
        return attribute.toString();
    }

    @Override
    public HttpUrl convertToEntityAttribute(String dbData) {
        return HttpUrl.fromString(dbData);
    }

}
