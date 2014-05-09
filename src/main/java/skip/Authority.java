package skip;

import javax.validation.constraints.Size;

public class Authority {
        
    @Size(max=50)
    private String username, authority;
    
    public Authority() {}
    
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    public String getAuthority() {
        return this.authority;
    }
    
}
