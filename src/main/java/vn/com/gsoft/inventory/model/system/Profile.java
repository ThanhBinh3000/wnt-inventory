package vn.com.gsoft.inventory.model.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Profile implements UserDetails, Serializable {
    private static final long serialVersionUID = 620L;
    private static final Log logger = LogFactory.getLog(Profile.class);
    @JsonIgnore
    private String password;
    private String username;
    private Set<CodeGrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long id;

    private String fullName;


    private NhaThuocs nhaThuoc;

    private List<Role> roles;

    private List<NhaThuocs> nhaThuocs;
    private List<Settings> settings;
    private List<ApplicationSetting> applicationSettings;


    public Profile(Long id, String fullName, NhaThuocs nhaThuoc, List<Role> roles, List<NhaThuocs> nhaThuocs,
                   String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Set<CodeGrantedAuthority> authorities,
                   List<Settings> settings,
                   List<ApplicationSetting> applicationSettings
    ) {
        this.id = id;
        this.fullName = fullName;
        this.nhaThuoc = nhaThuoc;
        this.roles = roles;
        this.nhaThuocs = nhaThuocs;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
        this.settings = settings;
        this.applicationSettings = applicationSettings;
    }
}
