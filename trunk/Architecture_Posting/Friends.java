package Architecture_Posting;

/*
 * Get pool detail info
 *
 * @param userId
 * @param poolId
 * @return PoolDetailInfo object
 * @throws ISException
 * 
 * @author Thanh Le Quoc
 */
public class Friends extends UserLoginObject {

    public Friends() {
    }
    private int countOffline;
    private String checkFriendsGroup;

    public int getCountOffline() {
        return countOffline;
    }

    public void setCountOffline(int countOffline) {
        this.countOffline = countOffline;
    }

    public String getCheckFriendsGroup() {
        return checkFriendsGroup;
    }

    public void setCheckFriendsGroup(String checkFriendsGroup) {
        this.checkFriendsGroup = checkFriendsGroup;
    }
}
