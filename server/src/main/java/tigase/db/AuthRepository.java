/*
 * AuthRepository.java
 *
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 */



package tigase.db;

//~--- non-JDK imports --------------------------------------------------------

import tigase.annotations.TigaseDeprecated;
import tigase.xmpp.BareJID;

import java.util.HashMap;
import java.util.Map;

//~--- JDK imports ------------------------------------------------------------

/**
 * Interface <code>AuthRepository</code> defines a proxy bridge between user
 * authentication data storage and the Tigase server authentication logic. Important
 * thing about the authentication repository is that it not only stores login credentials
 * but also performs actual user authentication.
 * This is because available authentication mechanisms depend on the way data are stored
 * in the repository (database).
 *
 * Created: Sun Nov  5 21:15:46 2006
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public interface AuthRepository extends Repository {
	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide an
	 * extra authentication data by the client to the authentication logic.
	 * Please note the <code>RESULT_KEY</code> property key is used to provide authentication
	 * data from the server to the client. This property is used to provide authentication data
	 * from the client to the server.
	 */
	public static final String DATA_KEY = "data";

	/** Field description */
	public static final String DIGEST_ID_KEY = "digest-id";

	/** Field description */
	public static final String DIGEST_KEY = "digest";

	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide
	 * desired authentication mechanism to the authentication logic.
	 */
	public static final String MACHANISM_KEY = "mechanism";

	/** Field description */
	public static final String PASSWORD_KEY = "password";

	// Query params (and otherAuth)

	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide
	 * desired authentication protocol to the authentication logic.
	 */
	public static final String PROTOCOL_KEY = "protocol";

	/**
	 * Property value for <code>otherAuth</code> method call. It is used to provide
	 * desired authentication NON-SASL protocol to the authentication logic.
	 */
	public static final String PROTOCOL_VAL_NONSASL = "nonsasl";

	/**
	 * Property value for <code>otherAuth</code> method call. It is used to provide
	 * desired authentication SASL protocol to the authentication logic.
	 */
	public static final String PROTOCOL_VAL_SASL = "sasl";

	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide
	 * authentication realm to the authentication logic. In most cases, the realm is just
	 * a domain name.
	 */
	public static final String REALM_KEY = "realm";

	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide
	 * authentication handshaking data during login process. Some authentication mechanisms
	 * require exchanging requests between the client and the server. This property key points
	 * back to the data which need to be sent back to the client.
	 */
	public static final String RESULT_KEY = "result";

	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide
	 * authentication domain to the authentication logic. It is highly recommended that this
	 * property is always set, even if the authentication protocol/mechanism does not need it
	 * strictly.
	 */
	public static final String SERVER_NAME_KEY = "server-name";

	/**
	 * Property key name for <code>otherAuth</code> method call. It is used to provide a user
	 * ID on successful user login. Please note, the key points to the object of
	 * <code>BareJID</code> type.
	 */
	public static final String USER_ID_KEY = "user-id";

	/** Field description */
	public static final String USERNAME_KEY = "username";

	//~--- methods --------------------------------------------------------------

	/**
	 * Describe <code>addUser</code> method here.
	 *
	 * @param user a <code>BareJID</code> value
	 * @param password a <code>String</code> value
	 * @exception UserExistsException if an error occurs
	 * @exception TigaseDBException if an error occurs
	 */
	void addUser(BareJID user, String password)
					throws UserExistsException, TigaseDBException;

	//~--- get methods ----------------------------------------------------------

	/**
	 * <code>getResourceUri</code> method returns database connection string.
	 *
	 * @return a <code>String</code> value of database connection string.
	 */
	String getResourceUri();

	/**
	 * This method is only used by the server statistics component to report
	 * number of registered users.
	 * @return a <code>long</code> number of registered users in the repository.
	 */
	long getUsersCount();

	/**
	 * This method is only used by the server statistics component to report
	 * number of registered users for given domain.
	 * @param domain
	 * @return a <code>long</code> number of registered users in the repository.
	 */
	long getUsersCount(String domain);

	/**
	 * Do some actions on repository, when user logs in. (for example update <code>last_login_time</code>)
	 * @param jid JID of logged user.
	 * @throws TigaseDBException if an error occurs
	 */
	void loggedIn(BareJID jid) throws TigaseDBException;

	//~--- methods --------------------------------------------------------------

	/**
	 * Describe <code>logout</code> method here.
	 *
	 * @param user a <code>BareJID</code> value
	 * @exception UserNotFoundException if an error occurs
	 * @exception TigaseDBException if an error occurs
	 */
	void logout(BareJID user) throws UserNotFoundException, TigaseDBException;

	/**
	 * Describe <code>otherAuth</code> method here.
	 *
	 * @param authProps
	 *            a <code>Map</code> value
	 * @return a <code>boolean</code> value
	 * @exception UserNotFoundException
	 *                if an error occurs
	 * @exception TigaseDBException
	 *                if an error occurs
	 * @exception AuthorizationException
	 *                if an error occurs
	 *
	 */
	@Deprecated
	@TigaseDeprecated(since = "8.0.0")
	boolean otherAuth(Map<String, Object> authProps)
					throws UserNotFoundException, TigaseDBException, AuthorizationException;

	/**
	 * <code>queryAuth</code> returns mechanisms available for authentication.
	 *
	 * @param authProps a <code>Map</code> value with parameters for authentication.
	 */
	@Deprecated
	@TigaseDeprecated(since = "8.0.0")
	void queryAuth(Map<String, Object> authProps);

	/**
	 * Describe <code>removeUser</code> method here.
	 *
	 * @param user a <code>BareJID</code> value
	 * @exception UserNotFoundException if an error occurs
	 * @exception TigaseDBException if an error occurs
	 */
	void removeUser(BareJID user) throws UserNotFoundException, TigaseDBException;

	 String getPassword(BareJID user)
			throws UserNotFoundException, TigaseDBException ;

	
	/**
	 * Describe <code>updatePassword</code> method here.
	 *
	 * @param user a <code>BareJID</code> value
	 * @param password a <code>String</code> value
	 * @throws UserNotFoundException
	 * @exception TigaseDBException if an error occurs
	 */
	void updatePassword(BareJID user, String password)
					throws UserNotFoundException, TigaseDBException;

	enum AccountStatus {
		active(1),
		disabled(0),
		pending(-2),
		system(-1),
		vip(2),
		paid(3);

		private static final HashMap<Integer, AccountStatus> statuses = new HashMap<>();

		static {
			for (AccountStatus v : AccountStatus.values()) {
				statuses.put(v.getValue(), v);
			}
		}

		private final int value;

		public static AccountStatus byValue(int value) {
			return statuses.get(value);
		}

		AccountStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	AccountStatus getAccountStatus(BareJID user) throws TigaseDBException;

	void setAccountStatus(BareJID user, AccountStatus status) throws TigaseDBException;

	@Deprecated
	@TigaseDeprecated(since = "8.0.0")
	boolean isUserDisabled(BareJID user) 
					throws UserNotFoundException, TigaseDBException;

	@Deprecated
	@TigaseDeprecated(since = "8.0.0")
	void setUserDisabled(BareJID user, Boolean value) 
					throws UserNotFoundException, TigaseDBException;

	default PasswordForm getPasswordForm(String domain) {
		return PasswordForm.plain;
	}

	enum PasswordForm {
		plain,
		encoded,
		unknown
	}
	
}    // AuthRepository


//~ Formatted in Tigase Code Convention on 13/02/20