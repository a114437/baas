package com.web.frame.config.swagger2;

import static com.google.common.collect.Maps.newTreeMap;
import static springfox.documentation.builders.BuilderDefaults.nullToEmptyList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import io.swagger.models.Contact;
import io.swagger.models.HttpMethod;
import io.swagger.models.Info;
import io.swagger.models.Path;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Documentation;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ResourceListing;
import springfox.documentation.swagger2.mappers.LicenseMapper;
import springfox.documentation.swagger2.mappers.ModelMapper;
import springfox.documentation.swagger2.mappers.ParameterMapper;
import springfox.documentation.swagger2.mappers.SecurityMapper;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.mappers.VendorExtensionsMapper;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-06-23T17:02:57-0500",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class ServiceModelToSwagger2MapperImplEx extends ServiceModelToSwagger2Mapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ParameterMapper parameterMapper;
    @Autowired
    private SecurityMapper securityMapper;
    @Autowired
    private LicenseMapper licenseMapper;
    @Autowired
    private VendorExtensionsMapper vendorExtensionsMapper;

    @Override
    public Swagger mapDocumentation(Documentation from) {
        if ( from == null ) {
            return null;
        }

        Swagger swagger = new Swagger();

        swagger.setVendorExtensions( vendorExtensionsMapper.mapExtensions( from.getVendorExtensions() ) );
        swagger.setSchemes( mapSchemes( from.getSchemes() ) );
        swagger.setHost( from.getHost() );
        Map<String, springfox.documentation.schema.Model> definitions = newTreeMap();
        for (ApiListing each : from.getApiListings().values()) {
          definitions.putAll(each.getModels());
        }
        swagger.setDefinitions( modelMapper.mapModels(definitions) );
        swagger.setSecurityDefinitions( securityMapper.toSecuritySchemeDefinitions( from.getResourceListing() ) );
        ApiInfo info = fromResourceListingInfo( from );
        if ( info != null ) {
            swagger.setInfo( mapApiInfo( info ) );
        }
        swagger.setBasePath( from.getBasePath() );
        swagger.setTags( tagSetToTagList( from.getTags() ) );
        List<String> list2 = from.getConsumes();
        if ( list2 != null ) {
            swagger.setConsumes( new ArrayList<String>( list2 ) );
        }
        else {
            swagger.setConsumes( null );
        }
        List<String> list3 = from.getProduces();
        if ( list3 != null ) {
            swagger.setProduces( new ArrayList<String>( list3 ) );
        }
        else {
            swagger.setProduces( null );
        }

        swagger.setPaths( mapApiListings_( from.getApiListings() ) );
        
        
        return swagger;
    }

    @Override
    protected Info mapApiInfo(ApiInfo from) {
        if ( from == null ) {
            return null;
        }

        Info info = new Info();

        info.setLicense( licenseMapper.apiInfoToLicense( from ) );
        info.setVendorExtensions( vendorExtensionsMapper.mapExtensions( from.getVendorExtensions() ) );
        info.setTermsOfService( from.getTermsOfServiceUrl() );
        info.setContact( map( from.getContact() ) );
        info.setDescription( from.getDescription() );
        info.setVersion( from.getVersion() );
        info.setTitle( from.getTitle() );

        return info;
    }

    @Override
    protected Contact map(springfox.documentation.service.Contact from) {
        if ( from == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setName( from.getName() );
        contact.setUrl( from.getUrl() );
        contact.setEmail( from.getEmail() );

        return contact;
    }

    @Override
    protected io.swagger.models.Operation mapOperation(Operation from) {
        if ( from == null ) {
            return null;
        }

        io.swagger.models.Operation operation = new io.swagger.models.Operation();

        operation.setSecurity( mapAuthorizations( from.getSecurityReferences() ) );
        operation.setVendorExtensions( vendorExtensionsMapper.mapExtensions( from.getVendorExtensions() ) );
        operation.setDescription( from.getNotes() );
        operation.setOperationId( from.getUniqueId() );
        operation.setResponses( mapResponseMessages( from.getResponseMessages() ) );
        operation.setSchemes( stringSetToSchemeList( from.getProtocol() ) );
        Set<String> set = from.getTags();
        if ( set != null ) {
            operation.setTags( new ArrayList<String>( set ) );
        }
        else {
            operation.setTags( null );
        }
        operation.setSummary( from.getSummary() );
        Set<String> set1 = from.getConsumes();
        if ( set1 != null ) {
            operation.setConsumes( new ArrayList<String>( set1 ) );
        }
        else {
            operation.setConsumes( null );
        }
        Set<String> set2 = from.getProduces();
        if ( set2 != null ) {
            operation.setProduces( new ArrayList<String>( set2 ) );
        }
        else {
            operation.setProduces( null );
        }
        operation.setParameters( parameterListToParameterList( from.getParameters() ) );
        if ( from.getDeprecated() != null ) {
            operation.setDeprecated( Boolean.parseBoolean( from.getDeprecated() ) );
        }

        return operation;
    }

    @Override
    protected Tag mapTag(springfox.documentation.service.Tag from) {
        if ( from == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setVendorExtensions( vendorExtensionsMapper.mapExtensions( from.getVendorExtensions() ) );
        tag.setName( from.getName() );
        tag.setDescription( from.getDescription() );

        return tag;
    }

    private ApiInfo fromResourceListingInfo(Documentation documentation) {
        if ( documentation == null ) {
            return null;
        }
        ResourceListing resourceListing = documentation.getResourceListing();
        if ( resourceListing == null ) {
            return null;
        }
        ApiInfo info = resourceListing.getInfo();
        if ( info == null ) {
            return null;
        }
        return info;
    }

    protected List<Tag> tagSetToTagList(Set<springfox.documentation.service.Tag> set) {
        if ( set == null ) {
            return null;
        }

        List<Tag> list = new ArrayList<Tag>( set.size() );
        for ( springfox.documentation.service.Tag tag : set ) {
        	Tag theTag = mapTag( tag );
            list.add( theTag );
        }
        
        Collections.sort(list, new Comparator<Tag>() {
			@Override
			public int compare(Tag tag1, Tag tag2) {
				try {
					String left = tag1.getName().split("//.")[0];
					String right = tag2.getName().split("//.")[0];
					return Integer.compare(Integer.parseInt(left), Integer.parseInt(right));
				} catch (Exception e) {
					return 0;
				}
			}
		});

        return list;
    }

    protected List<Scheme> stringSetToSchemeList(Set<String> set) {
        if ( set == null ) {
            return null;
        }

        List<Scheme> list = new ArrayList<Scheme>( set.size() );
        for ( String string : set ) {
            list.add( Enum.valueOf( Scheme.class, string ) );
        }

        return list;
    }

    protected List<Parameter> parameterListToParameterList(List<springfox.documentation.service.Parameter> list) {
        if ( list == null ) {
            return null;
        }

        List<Parameter> list1 = new ArrayList<Parameter>( list.size() );
        for ( springfox.documentation.service.Parameter parameter : list ) {
            list1.add( parameterMapper.mapParameter( parameter ) );
        }

        return list1;
    }
    
    
    protected Map<String, Path> mapApiListings_(Multimap<String, ApiListing> apiListings) {
        Map<String, Path> paths = new LinkedHashMap<>();
        Multimap<String, ApiListing> apiListingMap = LinkedListMultimap.create();
        Iterator iter = apiListings.entries().iterator();
        while(iter.hasNext())
        {
            Map.Entry<String, ApiListing> entry = (Map.Entry<String, ApiListing>)iter.next();
            ApiListing apis =  entry.getValue();
            List<ApiDescription> apiDesc = apis.getApis();
            List<ApiDescription> newApi = new ArrayList<>();
            for(ApiDescription a:apiDesc){
                newApi.add(a);
            }
            Collections.sort(newApi, new Comparator<ApiDescription>() {
                @Override
                public int compare(ApiDescription left, ApiDescription right) {
                	String leftNum = left.getOperations().get(0).getSummary().split("\\.")[0];
                	String rightNum = right.getOperations().get(0).getSummary().split("\\.")[0];
                	
                	int position = 0;
                	try {
                		position = Integer.compare(Integer.parseInt(leftNum), Integer.parseInt(rightNum));	
					} catch (Exception e) {
	                    int leftPos = left.getOperations().size() == 1 ? left.getOperations().get(0).getPosition() : 0;
	                    int rightPos = right.getOperations().size() == 1 ? right.getOperations().get(0).getPosition() : 0;
	                    position = Integer.compare(leftPos, rightPos);
	                    if(position == 0) {
	                        position = left.getPath().compareTo(right.getPath());
	                    }
					}
                    return position;
                }
            });
            try {
                //因ApiListing的属性都是final故需要通过反射来修改值
                modify(apis, "apis", newApi);
            } catch (Exception e) {
                e.printStackTrace();
            }
            apiListingMap.put(entry.getKey(),apis);
        }

        for (ApiListing each : apiListingMap.values()) {
            for (ApiDescription api : each.getApis()) {
                paths.put(api.getPath(), mapOperations(api, Optional.fromNullable(paths.get(api.getPath()))));
            }
        }
        return paths;
    }

    private Path mapOperations(ApiDescription api, Optional<Path> existingPath) {
        Path path = existingPath.or(new Path());
        for (springfox.documentation.service.Operation each : nullToEmptyList(api.getOperations())) {
            io.swagger.models.Operation operation = mapOperation(each);
            path.set(each.getMethod().toString().toLowerCase(), operation);
        }
        return path;
    }
    
    
    public static void modify(Object object, String fieldName, Object newFieldValue) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        if(!field.isAccessible()) {
            field.setAccessible(true);
        }

        field.set(object, newFieldValue);
    }
    
    
}