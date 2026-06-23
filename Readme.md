```
CREATE OR REPLACE FUNCTION public.update_player_country(
p_player_id bigint,
p_new_country varchar)
RETURNS integer
LANGUAGE 'plpgsql'
AS $$
DECLARE rows_affected INTEGER;
BEGIN
    UPDATE player
    SET country = p_new_country
    WHERE id = p_player_id;

	GET DIAGNOSTICS rows_affected = ROW_COUNT;
	RETURN rows_affected;	
END;
$$;
```