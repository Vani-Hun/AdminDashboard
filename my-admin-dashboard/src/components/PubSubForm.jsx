import PropTypes from 'prop-types';

export const PubSubForm = ({ formRef, onSubmit }) => (
    <form ref={formRef} className='google-pubsub-form' onSubmit={onSubmit}>
        <div className="form-group">
            <label htmlFor="clientId">CLIENT_ID</label>
            <input id="clientId" name="clientId" placeholder='Enter your Client ID' type="text" required />
        </div>
        <div className="form-group">
            <label htmlFor="clientSecret">CLIENT_SECRET</label>
            <input id="clientSecret" name="clientSecret" placeholder='Enter your Client Secret' type="password" required />
        </div>
        <div className="form-group">
            <label htmlFor="redirectUri">REDIRECT_URI</label>
            <input id="redirectUri" name="redirectUri" placeholder='Enter your Redirect URI' type="text" required />
        </div>
        <button type="submit">Register</button>
    </form>
);

PubSubForm.propTypes = {
    formRef: PropTypes.object.isRequired,
    onSubmit: PropTypes.func.isRequired,
};

