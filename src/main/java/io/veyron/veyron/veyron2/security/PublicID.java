package io.veyron.veyron.veyron2.security;

import io.veyron.veyron.veyron2.ipc.VeyronException;

import java.security.interfaces.ECPublicKey;

/**
 * PublicID is the interface for the non-secret component of a principal's unique identity.
 */
public interface PublicID {
	/**
	 * Returns a list of human-readable names associated with the principal.
	 * The returned names act only as a hint, there is no guarantee that they are
	 * globally unique.
	 */
	public String[] names();

	/**
	 * Returns the public key corresponding to the private key
	 * that is held only by the principal represented by this PublicID.
	 *
	 * @return public key held by the principal.
	 */
	public ECPublicKey publicKey();

	/**
	 * Determines whether the PublicID has credentials that are valid under the provided context.
	 * If so, Authorize returns a new PublicID that carries only the valid credentials. The returned
	 * PublicID is always non-nil in the absence of errors and has the same public key as this
	 * PublicID.
	 *
	 * @param  context         the context under which credentials are evaluated.
	 * @return                 the PublicID that carries only the valid credentials.
	 * @throws VeyronException if any error is encountered during the evaluation.
	 */
	public PublicID authorize(Context context) throws VeyronException;
}