<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Exception\InvalidArgumentException;
use Test\Types\PersonModel;

class UserModel extends PersonModel implements User
{
    /**
     * @var string
     */
    protected $role;

    /**
     * @return string
     */
    public function getRole()
    {
        if (is_null($this->role)) {
            $value = $this->raw(User::FIELD_ROLE);
            $value = (string)$value;
            $this->role = $value;
        }
        return $this->role;
    }

    /**
     * @param string $role
     * @return $this
     */
    public function setRole(string $role)
    {
        $this->role = (string)$role;

        return $this;
    }

}
